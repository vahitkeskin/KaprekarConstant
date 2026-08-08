package com.example.kaprekar.domain.usecase

import kotlin.math.PI

data class EratosthenesResult(
    val shadowAngleDegrees: Double,
    val distanceKm: Double,
    val calculatedCircumferenceKm: Double,
    val calculatedRadiusKm: Double,
    val realCircumferenceKm: Double,
    val accuracyPercentage: Double
)

class CalculateEratosthenesUseCase {
    operator fun invoke(
        shadowAngleDegrees: Double = 7.2,
        distanceKm: Double = 800.0
    ): EratosthenesResult {
        val safeAngle = if (shadowAngleDegrees <= 0) 7.2 else shadowAngleDegrees
        val safeDist = if (distanceKm <= 0) 800.0 else distanceKm

        // Circumference C = (360 / angle) * distance
        val cKm = (360.0 / safeAngle) * safeDist
        val rKm = cKm / (2.0 * PI)

        val realC = 40075.0 // Earth equatorial circumference in km
        val accuracy = 100.0 - (kotlin.math.abs(cKm - realC) / realC * 100.0)

        return EratosthenesResult(
            shadowAngleDegrees = safeAngle,
            distanceKm = safeDist,
            calculatedCircumferenceKm = cKm,
            calculatedRadiusKm = rKm,
            realCircumferenceKm = realC,
            accuracyPercentage = accuracy.coerceIn(0.0, 100.0)
        )
    }
}
