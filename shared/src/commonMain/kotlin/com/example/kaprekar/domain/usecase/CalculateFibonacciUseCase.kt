package com.example.kaprekar.domain.usecase

import kotlin.math.sqrt

data class FibonacciResult(
    val n: Int,
    val value: Long,
    val sequence: List<Long>,
    val ratioConvergence: List<FibonacciRatioItem>,
    val goldenRatioApproximation: Double
)

data class FibonacciRatioItem(
    val index: Int,
    val fn: Long,
    val fnMinus1: Long,
    val ratio: Double,
    val deltaFromGolden: Double
)

class CalculateFibonacciUseCase {

    companion object {
        const val GOLDEN_RATIO_CONSTANT = 1.618033988749895
    }

    fun execute(n: Int): FibonacciResult {
        val count = n.coerceIn(1, 60)
        val seq = ArrayList<Long>(count)
        
        var a = 0L
        var b = 1L
        
        for (i in 0 until count) {
            seq.add(b)
            val next = a + b
            a = b
            b = next
        }
        
        val ratios = ArrayList<FibonacciRatioItem>()
        for (i in 1 until seq.size) {
            val curr = seq[i]
            val prev = seq[i - 1]
            val r = curr.toDouble() / prev.toDouble()
            val delta = kotlin.math.abs(r - GOLDEN_RATIO_CONSTANT)
            ratios.add(
                FibonacciRatioItem(
                    index = i + 1,
                    fn = curr,
                    fnMinus1 = prev,
                    ratio = r,
                    deltaFromGolden = delta
                )
            )
        }

        val approx = if (seq.size >= 2) seq.last().toDouble() / seq[seq.size - 2].toDouble() else 1.0

        return FibonacciResult(
            n = count,
            value = seq.last(),
            sequence = seq,
            ratioConvergence = ratios,
            goldenRatioApproximation = approx
        )
    }
}
