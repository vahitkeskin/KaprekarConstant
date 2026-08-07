package com.example.kaprekar.domain.usecase

data class ModularResult(
    val base: Long,
    val exponent: Long,
    val modulus: Long,
    val modPowResult: Long,
    val eulerTotient: Long,
    val coprimes: List<Long>
)

class CalculateModularUseCase {
    operator fun invoke(base: Long, exponent: Long, modulus: Long): ModularResult {
        val modPow = modPow(base, exponent, modulus)
        val totient = eulerTotient(modulus)
        val coprimesList = mutableListOf<Long>()

        for (i in 1 until modulus) {
            if (gcd(i, modulus) == 1L) {
                if (coprimesList.size < 50) {
                    coprimesList.add(i)
                }
            }
        }

        return ModularResult(base, exponent, modulus, modPow, totient, coprimesList)
    }

    private fun modPow(base: Long, exp: Long, mod: Long): Long {
        if (mod == 1L) return 0L
        var res = 1L
        var b = base % mod
        var e = exp
        while (e > 0) {
            if (e % 2 == 1L) res = (res * b) % mod
            b = (b * b) % mod
            e /= 2
        }
        return res
    }

    private fun eulerTotient(n: Long): Long {
        var result = n
        var p = 2L
        var temp = n
        while (p * p <= temp) {
            if (temp % p == 0L) {
                while (temp % p == 0L) temp /= p
                result -= result / p
            }
            p++
        }
        if (temp > 1) result -= result / temp
        return result
    }

    private fun gcd(a: Long, b: Long): Long {
        var x = a
        var y = b
        while (y != 0L) {
            val t = y
            y = x % y
            x = t
        }
        return x
    }
}
