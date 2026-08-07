package com.example.kaprekar.domain.usecase

import kotlin.math.pow

data class EulerTaylorStep(val termIndex: Int, val factorial: Long, val termValue: Double, val partialSum: Double)

data class EulerResult(val estimatedE: Double, val limitSteps: List<Pair<Long, Double>>, val taylorSteps: List<EulerTaylorStep>)

class CalculateEulerUseCase {
    operator fun invoke(terms: Int = 12): EulerResult {
        // Limit convergence: (1 + 1/n)^n for n = 1, 10, 100, 1000, 10000, ...
        val limitSteps = mutableListOf<Pair<Long, Double>>()
        var n = 1L
        for (i in 1..7) {
            val valN = (1.0 + 1.0 / n).pow(n.toDouble())
            limitSteps.add(n to valN)
            n *= 10L
        }

        // Taylor expansion e = 1/0! + 1/1! + 1/2! + 1/3! + ...
        val taylorSteps = mutableListOf<EulerTaylorStep>()
        var sum = 0.0
        var fact = 1L

        for (k in 0..terms) {
            if (k > 0) fact *= k
            val term = 1.0 / fact
            sum += term
            taylorSteps.add(EulerTaylorStep(k, fact, term, sum))
        }

        return EulerResult(sum, limitSteps, taylorSteps)
    }
}
