package com.example.kaprekar.domain.usecase

data class EuclidStep(val stepNumber: Int, val dividend: Long, val divisor: Long, val quotient: Long, val remainder: Long)

data class EuclidResult(val num1: Long, val num2: Long, val gcd: Long, val lcm: Long, val steps: List<EuclidStep>)

class CalculateEuclidGcdUseCase {
    operator fun invoke(a: Long, b: Long): EuclidResult {
        var numA = kotlin.math.abs(a)
        var numB = kotlin.math.abs(b)

        if (numA < numB) {
            val temp = numA
            numA = numB
            numB = temp
        }

        val steps = mutableListOf<EuclidStep>()
        var stepCount = 1
        var currA = numA
        var currB = numB

        while (currB != 0L) {
            val q = currA / currB
            val r = currA % currB
            steps.add(EuclidStep(stepCount++, currA, currB, q, r))
            currA = currB
            currB = r
        }

        val gcd = if (currA == 0L) 1L else currA
        val lcm = if (gcd == 0L) 0L else (numA / gcd) * numB

        return EuclidResult(numA, numB, gcd, lcm, steps)
    }
}
