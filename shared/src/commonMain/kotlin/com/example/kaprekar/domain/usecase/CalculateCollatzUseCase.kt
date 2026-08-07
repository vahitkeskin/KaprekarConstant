package com.example.kaprekar.domain.usecase

data class CollatzStep(
    val stepIndex: Int,
    val currentValue: Long,
    val isEven: Boolean,
    val formula: String
)

data class CollatzResult(
    val initialNumber: Long,
    val stepCount: Int,
    val peakValue: Long,
    val steps: List<CollatzStep>
)

class CalculateCollatzUseCase {

    fun execute(startNum: Long): CollatzResult {
        var curr = startNum.coerceAtLeast(1)
        val steps = ArrayList<CollatzStep>()
        var stepCount = 0
        var peak = curr

        steps.add(
            CollatzStep(
                stepIndex = 0,
                currentValue = curr,
                isEven = curr % 2 == 0L,
                formula = "Başlangıç: $curr"
            )
        )

        while (curr > 1 && stepCount < 1000) {
            val isEven = curr % 2 == 0L
            val nextVal = if (isEven) {
                curr / 2
            } else {
                3 * curr + 1
            }

            stepCount++
            val formulaStr = if (isEven) "$curr / 2 = $nextVal" else "3 × $curr + 1 = $nextVal"
            curr = nextVal
            if (curr > peak) peak = curr

            steps.add(
                CollatzStep(
                    stepIndex = stepCount,
                    currentValue = curr,
                    isEven = curr % 2 == 0L,
                    formula = formulaStr
                )
            )
        }

        return CollatzResult(
            initialNumber = startNum,
            stepCount = stepCount,
            peakValue = peak,
            steps = steps
        )
    }
}
