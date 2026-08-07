package com.example.kaprekar.domain.usecase

data class PascalResult(
    val rowCount: Int,
    val rows: List<List<Long>>,
    val n: Int,
    val k: Int,
    val combinationVal: Long,
    val permutationVal: Long,
    val combinationFormula: String
)

class CalculatePascalUseCase {

    fun execute(rowsCount: Int, n: Int, k: Int): PascalResult {
        val rCount = rowsCount.coerceIn(1, 15)
        val triangle = ArrayList<List<Long>>()
        
        for (i in 0 until rCount) {
            val row = ArrayList<Long>()
            for (j in 0..i) {
                if (j == 0 || j == i) {
                    row.add(1L)
                } else {
                    val prevRow = triangle[i - 1]
                    row.add(prevRow[j - 1] + prevRow[j])
                }
            }
            triangle.add(row)
        }

        val safeN = n.coerceIn(0, 20)
        val safeK = k.coerceIn(0, safeN)

        val comb = combination(safeN, safeK)
        val perm = permutation(safeN, safeK)

        val formula = "C($safeN, $safeK) = $safeN! / ($safeK! × (${safeN - safeK})!) = $comb"

        return PascalResult(
            rowCount = rCount,
            rows = triangle,
            n = safeN,
            k = safeK,
            combinationVal = comb,
            permutationVal = perm,
            combinationFormula = formula
        )
    }

    private fun combination(n: Int, k: Int): Long {
        if (k < 0 || k > n) return 0L
        if (k == 0 || k == n) return 1L
        var res = 1L
        val minK = if (k < n - k) k else n - k
        for (i in 1..minK) {
            res = res * (n - i + 1) / i
        }
        return res
    }

    private fun permutation(n: Int, k: Int): Long {
        if (k < 0 || k > n) return 0L
        var res = 1L
        for (i in 0 until k) {
            res *= (n - i)
        }
        return res
    }
}
