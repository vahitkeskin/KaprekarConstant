package com.example.kaprekar.domain.usecase

data class GodelSymbolFactor(val prime: Long, val symbolChar: Char, val asciiValue: Int, val termPower: String)

data class GodelResult(
    val inputFormula: String,
    val godelNumberRepresentation: String,
    val primeFactors: List<GodelSymbolFactor>
)

class CalculateGodelUseCase {
    private val primes = listOf(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L)

    operator fun invoke(formula: String = "x+y=z"): GodelResult {
        val safeFormula = if (formula.isBlank()) "6174" else formula.take(10)
        val factors = mutableListOf<GodelSymbolFactor>()
        val terms = mutableListOf<String>()

        for (i in safeFormula.indices) {
            val char = safeFormula[i]
            val ascii = char.code
            val prime = if (i < primes.size) primes[i] else 53L
            val termStr = "$prime^$ascii"
            terms.add(termStr)
            factors.add(GodelSymbolFactor(prime, char, ascii, termStr))
        }

        val godelStr = terms.joinToString(" × ")

        return GodelResult(safeFormula, godelStr, factors)
    }
}
