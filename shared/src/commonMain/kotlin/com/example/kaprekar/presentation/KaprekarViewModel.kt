package com.example.kaprekar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaprekar.domain.usecase.CalculateKaprekarUseCase
import com.example.kaprekar.domain.usecase.ValidationResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class KaprekarViewModel(
    private val calculateKaprekarUseCase: CalculateKaprekarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(KaprekarUiState())
    val uiState: StateFlow<KaprekarUiState> = _uiState.asStateFlow()

    private var animationJob: Job? = null

    fun onIntent(intent: KaprekarUiIntent) {
        when (intent) {
            is KaprekarUiIntent.OnInputChanged -> handleInputChanged(intent.newInput)
            is KaprekarUiIntent.OnCalculateClicked -> calculateSteps()
            is KaprekarUiIntent.OnPresetSelected -> handlePresetSelected(intent.preset)
            is KaprekarUiIntent.OnResetClicked -> handleReset()
            is KaprekarUiIntent.OnToggleInfoDialog -> handleToggleInfoDialog(intent.show)
        }
    }

    private fun handleInputChanged(rawInput: String) {
        val digitsOnly = rawInput.filter { it.isDigit() }.take(4)
        
        val validationMsg = if (digitsOnly.length == 4) {
            when (val res = calculateKaprekarUseCase.validateInput(digitsOnly)) {
                is ValidationResult.Error -> res.message
                is ValidationResult.Success -> null
            }
        } else if (digitsOnly.isNotEmpty()) {
            "4 basamaklı bir sayı girin (örn. 6825)"
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
        val validation = calculateKaprekarUseCase.validateInput(currentInput)
        
        if (validation is ValidationResult.Error) {
            _uiState.update { it.copy(validationError = validation.message) }
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

        // Animate sequential step reveal with slow delay (1000ms per step) for clear observation
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
            KaprekarUiState()
        }
    }

    private fun handleToggleInfoDialog(show: Boolean) {
        _uiState.update {
            it.copy(showInfoDialog = show)
        }
    }
}
