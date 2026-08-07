package com.example.kaprekar.domain.usecase

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class FourierHarmonic(val n: Int, val radius: Double, val freq: Double, val phase: Double)

data class FourierResult(val harmonics: List<FourierHarmonic>, val wavePoints: List<Double>)

class CalculateFourierUseCase {
    operator fun invoke(harmonicsCount: Int = 5, waveType: String = "SQUARE"): FourierResult {
        val list = mutableListOf<FourierHarmonic>()
        val wave = mutableListOf<Double>()

        for (i in 0 until harmonicsCount) {
            val n = if (waveType == "SQUARE") 2 * i + 1 else i + 1
            val radius = if (waveType == "SQUARE") (4.0 / (n * PI)) else (2.0 / (n * PI))
            list.add(FourierHarmonic(n, radius, n.toDouble(), 0.0))
        }

        for (t in 0..200) {
            val time = t * 0.05
            var ySum = 0.0
            for (h in list) {
                ySum += h.radius * sin(h.freq * time)
            }
            wave.add(ySum)
        }

        return FourierResult(list, wave)
    }
}
