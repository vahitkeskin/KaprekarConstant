package com.example.kaprekar.domain.usecase

data class NimMove(val heapIndex: Int, val removeAmount: Int)

data class NimResult(val heaps: List<Int>, val nimSum: Int, val isWinningPosition: Boolean, val recommendedMove: NimMove?)

class CalculateNimGameUseCase {
    operator fun invoke(heaps: List<Int>): NimResult {
        var nimSum = 0
        for (h in heaps) {
            nimSum = nimSum xor h
        }

        val isWinning = nimSum != 0
        var bestMove: NimMove? = null

        if (isWinning) {
            for (i in heaps.indices) {
                val target = heaps[i] xor nimSum
                if (target < heaps[i]) {
                    bestMove = NimMove(i, heaps[i] - target)
                    break
                }
            }
        }

        return NimResult(heaps, nimSum, isWinning, bestMove)
    }
}
