package com.example.kaprekar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaprekar.domain.model.AppLanguage
import com.example.kaprekar.domain.repository.ThemeRepository
import com.example.kaprekar.domain.usecase.CalculateKaprekarUseCase
import com.example.kaprekar.domain.usecase.ValidationResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class KaprekarViewModel(
    private val calculateKaprekarUseCase: CalculateKaprekarUseCase,
    private val themeRepository: ThemeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(KaprekarUiState())
    val uiState: StateFlow<KaprekarUiState> = _uiState.asStateFlow()

    private var animationJob: Job? = null

    init {
        combine(themeRepository.themeMode, themeRepository.appLanguage) { mode, lang ->
            mode to lang
        }.onEach { (mode, lang) ->
            _uiState.update {
                it.copy(
                    themeMode = mode,
                    appLanguage = lang,
                    isInitializingPreferences = false
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onIntent(intent: KaprekarUiIntent) {
        when (intent) {
            is KaprekarUiIntent.OnInputChanged -> handleInputChanged(intent.newInput)
            is KaprekarUiIntent.OnCalculateClicked -> calculateSteps()
            is KaprekarUiIntent.OnPresetSelected -> handlePresetSelected(intent.preset)
            is KaprekarUiIntent.OnResetClicked -> handleReset()
            is KaprekarUiIntent.OnToggleInfoDialog -> handleToggleInfoDialog(intent.show)
            is KaprekarUiIntent.OnToggleLanguageDialog -> handleToggleLanguageDialog(intent.show)
            is KaprekarUiIntent.OnToggleThemeMode -> toggleThemeMode()
            is KaprekarUiIntent.OnSelectLanguage -> selectLanguage(intent.language)
        }
    }

    private fun toggleThemeMode() {
        viewModelScope.launch {
            val nextMode = _uiState.value.themeMode.next()
            themeRepository.setThemeMode(nextMode)
        }
    }

    private fun selectLanguage(language: AppLanguage) {
        viewModelScope.launch {
            themeRepository.setAppLanguage(language)
            _uiState.update { it.copy(showLanguageDialog = false) }
            // Re-validate current input with updated strings
            if (_uiState.value.inputNumber.isNotEmpty()) {
                handleInputChanged(_uiState.value.inputNumber)
            }
        }
    }

    private fun handleInputChanged(rawInput: String) {
        val digitsOnly = rawInput.filter { it.isDigit() }.take(4)
        val strings = _uiState.value.strings
        
        val validationMsg = if (digitsOnly.length == 4) {
            when (calculateKaprekarUseCase.validateInput(digitsOnly)) {
                is ValidationResult.Error -> strings.validationDistinctDigits
                is ValidationResult.Success -> null
            }
        } else if (digitsOnly.isNotEmpty()) {
            strings.validationProgressHint
        } else {
            null
        }

        _uiState.update {
            it.copy(
                inputNumber = digitsOnly,
                validationError = validationMsg
            )
        }
    }

    private fun handlePresetSelected(preset: String) {
        handleInputChanged(preset)
        calculateSteps()
    }

    private fun calculateSteps() {
        val currentInput = _uiState.value.inputNumber
        val strings = _uiState.value.strings
        val validation = calculateKaprekarUseCase.validateInput(currentInput)
        
        if (validation is ValidationResult.Error) {
            _uiState.update { it.copy(validationError = strings.validationDistinctDigits) }
            return
        }

        animationJob?.cancel()

        _uiState.update {
            it.copy(
                isCalculating = true,
                validationError = null,
                steps = emptyList(),
                visibleStepCount = 0,
                isCompleted = false,
                reachedConstant = false
            )
        }

        val computedSteps = calculateKaprekarUseCase.execute(currentInput)
        val reachesKaprekar = computedSteps.lastOrNull()?.isKaprekarConstant == true

        _uiState.update {
            it.copy(
                steps = computedSteps,
                reachedConstant = reachesKaprekar
            )
        }

        // Animate sequential step reveal with slow delay (1000ms per step)
        animationJob = viewModelScope.launch {
            for (i in 1..computedSteps.size) {
                delay(1000)
                _uiState.update { currentState ->
                    currentState.copy(visibleStepCount = i)
                }
            }
            _uiState.update { currentState ->
                currentState.copy(
                    isCalculating = false,
                    isCompleted = true
                )
            }
        }
    }

    private fun handleReset() {
        animationJob?.cancel()
        _uiState.update {
            it.copy(
                inputNumber = "",
                validationError = null,
                steps = emptyList(),
                visibleStepCount = 0,
                isCalculating = false,
                isCompleted = false,
                reachedConstant = false
            )
        }
    }

    private fun handleToggleInfoDialog(show: Boolean) {
        _uiState.update {
            it.copy(showInfoDialog = show)
        }
    }

    private fun handleToggleLanguageDialog(show: Boolean) {
        _uiState.update {
            it.copy(showLanguageDialog = show)
        }
    }
}
