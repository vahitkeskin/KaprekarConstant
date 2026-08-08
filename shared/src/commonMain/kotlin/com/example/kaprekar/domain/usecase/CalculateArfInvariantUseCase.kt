package com.example.kaprekar.domain.usecase

data class ArfInvariantResult(
    val a: Int,
    val b: Int,
    val c: Int,
    val arfValue: Int,
    val formulaExplanation: String,
    val cahitArfInfo: String
)

class CalculateArfInvariantUseCase {
    operator fun invoke(a: Int = 1, b: Int = 1, c: Int = 1): ArfInvariantResult {
        // Binary quadratic form Q(x, y) = a*x^2 + b*x*y + c*y^2 over Z_2
        val modA = (a % 2 + 2) % 2
        val modB = (b % 2 + 2) % 2
        val modC = (c % 2 + 2) % 2

        // Arf Invariant Arf(Q) = a * c mod 2 (for standard symplectic pair)
        val arf = (modA * modC) % 2

        val explanation = "Arf(Q) = (a × c) mod 2 = ($modA × $modC) mod 2 = $arf"
        val info = "Cahit Arf (1910–1997) discovered the Arf Invariant in 1941 for quadratic forms over fields of characteristic 2. It is featured on the 10 Turkish Lira banknote!"

        return ArfInvariantResult(modA, modB, modC, arf, explanation, info)
    }
}
