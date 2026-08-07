package com.example.kaprekar.presentation

import com.example.kaprekar.domain.model.KaprekarStep

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
    val showInfoDialog: Boolean = false
) {
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
}
