package com.example.kaprekar.domain.usecase

data class SuperNumberPropertyResult(
    val propertyName: String,
    val isMatched: Boolean,
    val explanation: String,
    val formulaDetail: String
)

data class SuperNumberAnalysisResult(
    val number: Long,
    val isArmstrong: Boolean,
    val isPerfect: Boolean,
    val isHarshad: Boolean,
    val isKaprekar: Boolean,
    val isAutomorphic: Boolean,
    val properties: List<SuperNumberPropertyResult>
)

class CalculateSuperNumberUseCase {

    fun execute(num: Long): SuperNumberAnalysisResult {
        val n = num.coerceAtLeast(1)
        val numStr = n.toString()
        val digitsCount = numStr.length

        // Armstrong Check
        val armstrongSum = numStr.sumOf { charDigit ->
            var pow = 1L
            val digitVal = charDigit.digitToInt().toLong()
            repeat(digitsCount) { pow *= digitVal }
            pow
        }
        val isArmstrong = (armstrongSum == n)
        val armstrongDetail = numStr.map { "$it^$digitsCount" }.joinToString(" + ") + " = $armstrongSum"

        // Perfect Number Check
        var divisorSum = 0L
        if (n in 1..1_000_000) {
            for (i in 1 until n) {
                if (n % i == 0L) divisorSum += i
            }
        }
        val isPerfect = (divisorSum == n && n > 1)
        val perfectDetail = "Çarpanlar Toplamı = $divisorSum"

        // Harshad (Niven) Check
        val digitSum = numStr.sumOf { it.digitToInt() }
        val isHarshad = (digitSum > 0 && n % digitSum == 0L)
        val harshadDetail = "$n / (Rakamlar Toplamı: $digitSum) = ${if (isHarshad) n / digitSum else "Kalanlı"}"

        // Kaprekar Number Check (n^2 split into 2 parts summing to n)
        val sq = n * n
        val sqStr = sq.toString()
        var isKaprekar = false
        var kaprekarDetail = "$n² = $sq"

        if (n == 1L) {
            isKaprekar = true
            kaprekarDetail = "1² = 1 (Özel Kaprekar)"
        } else {
            for (i in 1 until sqStr.length) {
                val left = sqStr.substring(0, i).toLongOrNull() ?: 0L
                val right = sqStr.substring(i).toLongOrNull() ?: 0L
                if (right > 0 && left + right == n) {
                    isKaprekar = true
                    kaprekarDetail = "$n² = $sq → $left + $right = $n"
                    break
                }
            }
        }

        // Automorphic Number Check (n^2 ends in n)
        val isAutomorphic = sqStr.endsWith(numStr)
        val automorphicDetail = "$n² = $sq (Son basamaklar ${if (isAutomorphic) "uyuşuyor" else "farklı"})"

        val propList = listOf(
            SuperNumberPropertyResult(
                propertyName = "Armstrong Sayısı",
                isMatched = isArmstrong,
                explanation = "Rakamlarının, basamak sayısı kuvvetleri toplamı kendisine eşittir.",
                formulaDetail = armstrongDetail
            ),
            SuperNumberPropertyResult(
                propertyName = "Mükemmel Sayı",
                isMatched = isPerfect,
                explanation = "Kendisi hariç pozitif tam bölenlerinin toplamı kendisine eşittir.",
                formulaDetail = perfectDetail
            ),
            SuperNumberPropertyResult(
                propertyName = "Harshad (Niven) Sayısı",
                isMatched = isHarshad,
                explanation = "Rakamları toplamına tam bölünebilen sayıdır.",
                formulaDetail = harshadDetail
            ),
            SuperNumberPropertyResult(
                propertyName = "Kaprekar Sayısı",
                isMatched = isKaprekar,
                explanation = "Karesi iki parçaya bölündüğünde toplamı orijinal sayıyı verir.",
                formulaDetail = kaprekarDetail
            ),
            SuperNumberPropertyResult(
                propertyName = "Otomorfik Sayı",
                isMatched = isAutomorphic,
                explanation = "Karesinin son basamakları sayının kendisi ile biter.",
                formulaDetail = automorphicDetail
            )
        )

        return SuperNumberAnalysisResult(
            number = n,
            isArmstrong = isArmstrong,
            isPerfect = isPerfect,
            isHarshad = isHarshad,
            isKaprekar = isKaprekar,
            isAutomorphic = isAutomorphic,
            properties = propList
        )
    }
}
