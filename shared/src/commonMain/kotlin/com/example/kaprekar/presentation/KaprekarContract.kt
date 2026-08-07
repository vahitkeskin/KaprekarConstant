package com.example.kaprekar.presentation

import com.example.kaprekar.domain.model.AppLanguage
import com.example.kaprekar.domain.model.KaprekarStep
import com.example.kaprekar.domain.model.ThemeMode
import com.example.kaprekar.presentation.i18n.AppStrings
import com.example.kaprekar.presentation.i18n.StringsProvider

/**
 * UI State for Kaprekar Screen following UDF / MVI pattern.
 */
data class KaprekarUiState(
    val inputNumber: String = "",
    val validationError: String? = null,
    val steps: List<KaprekarStep> = emptyList(),
    val visibleStepCount: Int = 0,
    val isCalculating: Boolean = false,
    val isCompleted: Boolean = false,
    val reachedConstant: Boolean = false,
    val showInfoDialog: Boolean = false,
    val showLanguageDialog: Boolean = false,
    val isInitializingPreferences: Boolean = true,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val appLanguage: AppLanguage = AppLanguage.getSystemDefault()
) {
    val strings: AppStrings
        get() = StringsProvider.getStrings(appLanguage)

    val isValidInput: Boolean
        get() = inputNumber.length == 4 && inputNumber.all { it.isDigit() } && inputNumber.toSet().size >= 2
}

/**
 * UI Intents / User Actions.
 */
sealed interface KaprekarUiIntent {
    data class OnInputChanged(val newInput: String) : KaprekarUiIntent
    data object OnCalculateClicked : KaprekarUiIntent
    data class OnPresetSelected(val preset: String) : KaprekarUiIntent
    data object OnResetClicked : KaprekarUiIntent
    data class OnToggleInfoDialog(val show: Boolean) : KaprekarUiIntent
    data class OnToggleLanguageDialog(val show: Boolean) : KaprekarUiIntent
    data object OnToggleThemeMode : KaprekarUiIntent
    data class OnSelectLanguage(val language: AppLanguage) : KaprekarUiIntent
}
