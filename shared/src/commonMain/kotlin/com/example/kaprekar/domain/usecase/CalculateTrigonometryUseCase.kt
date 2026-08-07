package com.example.kaprekar.domain.usecase

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

data class TrigonometryResult(
    val degrees: Double,
    val radians: Double,
    val sinVal: Double,
    val cosVal: Double,
    val tanVal: Double?,
    val cotVal: Double?,
    val secVal: Double?,
    val cscVal: Double?,
    val quadrant: Int
)

class CalculateTrigonometryUseCase {
    operator fun invoke(degrees: Double): TrigonometryResult {
        val rad = degrees * PI / 180.0
        val s = sin(rad)
        val c = cos(rad)
        val t = if (kotlin.math.abs(c) < 1e-10) null else tan(rad)
        val cot = if (kotlin.math.abs(s) < 1e-10) null else c / s
        val sec = if (kotlin.math.abs(c) < 1e-10) null else 1.0 / c
        val csc = if (kotlin.math.abs(s) < 1e-10) null else 1.0 / s

        val normDeg = (degrees % 360 + 360) % 360
        val quad = when {
            normDeg in 0.0..90.0 -> 1
            normDeg in 90.0..180.0 -> 2
            normDeg in 180.0..270.0 -> 3
            else -> 4
        }

        return TrigonometryResult(degrees, rad, s, c, t, cot, sec, csc, quad)
    }
}
