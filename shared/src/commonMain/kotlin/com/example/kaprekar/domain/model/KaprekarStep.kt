package com.example.kaprekar.domain.model

/**
 * Represents a single step in Kaprekar's routine.
 */
data class KaprekarStep(
    val stepNumber: Int,
    val inputNumber: String,
    val descending: String,
    val ascending: String,
    val descendingValue: Int,
    val ascendingValue: Int,
    val resultValue: Int,
    val resultString: String,
    val formula: String,
    val isKaprekarConstant: Boolean = (resultValue == 6174)
)
