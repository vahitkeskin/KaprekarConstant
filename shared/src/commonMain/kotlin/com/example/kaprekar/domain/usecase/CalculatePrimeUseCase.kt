package com.example.kaprekar.domain.usecase

data class PrimeFactor(
    val prime: Long,
    val exponent: Int
)

data class SieveItem(
    val number: Int,
    val isPrime: Boolean
)

data class PrimeResult(
    val number: Long,
    val isPrime: Boolean,
    val factors: List<PrimeFactor>,
    val factorizationString: String,
    val sieveGrid: List<SieveItem>
)

class CalculatePrimeUseCase {

    fun execute(num: Long, sieveLimit: Int = 100): PrimeResult {
        val n = num.coerceAtLeast(1)
        val isP = checkPrime(n)
        val factors = factorize(n)
        val factStr = if (n == 1L) "1 (Asal Değil)" else if (isP) "$n (Asal Sayı)" else factors.joinToString(" × ") {
            if (it.exponent > 1) "${it.prime}^${it.exponent}" else "${it.prime}"
        }

        val limit = sieveLimit.coerceIn(10, 200)
        val isPrimeArray = BooleanArray(limit + 1) { true }
        if (limit >= 0) isPrimeArray[0] = false
        if (limit >= 1) isPrimeArray[1] = false

        var p = 2
        while (p * p <= limit) {
            if (isPrimeArray[p]) {
                var i = p * p
                while (i <= limit) {
                    isPrimeArray[i] = false
                    i += p
                }
            }
            p++
        }

        val grid = (1..limit).map {
            SieveItem(number = it, isPrime = isPrimeArray[it])
        }

        return PrimeResult(
            number = n,
            isPrime = isP,
            factors = factors,
            factorizationString = factStr,
            sieveGrid = grid
        )
    }

    private fun checkPrime(n: Long): Boolean {
        if (n <= 1) return false
        if (n <= 3) return true
        if (n % 2 == 0L || n % 3 == 0L) return false
        var i = 5L
        while (i * i <= n) {
            if (n % i == 0L || n % (i + 2) == 0L) return false
            i += 6
        }
        return true
    }

    private fun factorize(n: Long): List<PrimeFactor> {
        var temp = n
        val list = ArrayList<PrimeFactor>()
        var d = 2L
        while (d * d <= temp) {
            if (temp % d == 0L) {
                var count = 0
                while (temp % d == 0L) {
                    count++
                    temp /= d
                }
                list.add(PrimeFactor(d, count))
            }
            d++
        }
        if (temp > 1) {
            list.add(PrimeFactor(temp, 1))
        }
        return list
    }
}
