package com.example.kaprekar.domain.usecase

import kotlin.math.log10
import kotlin.math.ln

data class LogarithmResult(
    val baseLog: Double,
    val naturalLog: Double,
    val log10Value: Double,
    val richterMagnitude: Double,
    val decibelSound: Double,
    val phValue: Double
)

class CalculateLogarithmUseCase {
    operator fun invoke(valX: Double = 100.0, base: Double = 2.0): LogarithmResult {
        val safeX = if (valX <= 0) 1.0 else valX
        val safeBase = if (base <= 0 || base == 1.0) 2.0 else base

        val logB = ln(safeX) / ln(safeBase)
        val natLog = ln(safeX)
        val logTen = log10(safeX)

        // Richter Magnitude: M = log10(A) + 3 (assuming relative amplitude A)
        val richter = log10(safeX) + 3.0

        // Decibel: dB = 10 * log10(I / I0) where I0 = 10^-12
        val decibel = 10.0 * (log10(safeX) + 12.0)

        // pH: -log10[H+] for H+ concentration (e.g. 10^-7 = 7)
        val ph = -log10(1.0 / safeX)

        return LogarithmResult(logB, natLog, logTen, richter, decibel, ph)
    }
}
