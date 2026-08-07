package com.example.kaprekar.domain.usecase

import com.example.kaprekar.domain.model.KaprekarStep

sealed interface ValidationResult {
    data object Success : ValidationResult
    data class Error(val message: String) : ValidationResult
}

class CalculateKaprekarUseCase {

    companion object {
        const val KAPREKAR_CONSTANT = 6174
        const val MAX_ITERATIONS = 8
    }

    /**
     * Validates if the given string input is suitable for Kaprekar's Routine.
     */
    fun validateInput(input: String): ValidationResult {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) {
            return ValidationResult.Error("Lütfen 4 basamaklı bir sayı girin.")
        }
        if (!trimmed.all { it.isDigit() }) {
            return ValidationResult.Error("Sadece rakam girebilirsiniz.")
        }
        if (trimmed.length != 4) {
            return ValidationResult.Error("Girdi tam olarak 4 basamaklı olmalıdır.")
        }
        if (trimmed.toSet().size < 2) {
            return ValidationResult.Error("Sayı en az iki farklı rakam içermelidir (örn. 1111 olamaz).")
        }
        return ValidationResult.Success
    }

    /**
     * Executes Kaprekar's Routine step by step up to [MAX_ITERATIONS].
     * Returns a list of [KaprekarStep] representing the computational process.
     */
    fun execute(inputNumber: String): List<KaprekarStep> {
        val validation = validateInput(inputNumber)
        if (validation is ValidationResult.Error) {
            throw IllegalArgumentException(validation.message)
        }

        val steps = mutableListOf<KaprekarStep>()
        var current = inputNumber.padStart(4, '0')
        var stepCount = 1

        while (stepCount <= MAX_ITERATIONS) {
            val digits = current.toCharArray()
            val descendingStr = digits.sortedDescending().joinToString("")
            val ascendingStr = digits.sorted().joinToString("")

            val descVal = descendingStr.toInt()
            val ascVal = ascendingStr.toInt()
            val resultVal = descVal - ascVal
            val resultStr = resultVal.toString().padStart(4, '0')

            val formula = "$descendingStr - $ascendingStr = $resultStr"

            val step = KaprekarStep(
                stepNumber = stepCount,
                inputNumber = current,
                descending = descendingStr,
                ascending = ascendingStr,
                descendingValue = descVal,
                ascendingValue = ascVal,
                resultValue = resultVal,
                resultString = resultStr,
                formula = formula
            )

            steps.add(step)

            if (resultVal == KAPREKAR_CONSTANT) {
                break
            }

            current = resultStr
            stepCount++
        }

        return steps
    }
}
