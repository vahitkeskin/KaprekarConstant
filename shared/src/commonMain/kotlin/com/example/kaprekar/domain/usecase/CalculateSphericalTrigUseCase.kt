package com.example.kaprekar.domain.usecase

import kotlin.math.*

data class SphericalTrigResult(
    val lat1Deg: Double, val lon1Deg: Double,
    val lat2Deg: Double, val lon2Deg: Double,
    val distanceKm: Double,
    val initialBearingDeg: Double,
    val sphericalCosineResultKm: Double
)

class CalculateSphericalTrigUseCase {
    operator fun invoke(
        lat1: Double = 41.0082, lon1: Double = 28.9784, // Istanbul
        lat2: Double = 51.5074, lon2: Double = -0.1278   // London
    ): SphericalTrigResult {
        val rEarth = 6371.0 // Earth radius in km

        val phi1 = toRad(lat1)
        val phi2 = toRad(lat2)
        val deltaPhi = toRad(lat2 - lat1)
        val deltaLambda = toRad(lon2 - lon1)

        // Haversine formula
        val a = sin(deltaPhi / 2.0).pow(2.0) + cos(phi1) * cos(phi2) * sin(deltaLambda / 2.0).pow(2.0)
        val c = 2.0 * atan2(sqrt(a), sqrt(1.0 - a))
        val haversineDist = rEarth * c

        // Spherical Law of Cosines d = r * acos( sin(phi1)sin(phi2) + cos(phi1)cos(phi2)cos(deltaLambda) )
        val cosD = sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(deltaLambda)
        val sphericalDist = rEarth * acos(cosD.coerceIn(-1.0, 1.0))

        // Initial bearing
        val y = sin(deltaLambda) * cos(phi2)
        val x = cos(phi1) * sin(phi2) - sin(phi1) * cos(phi2) * cos(deltaLambda)
        val bearing = (toDeg(atan2(y, x)) + 360.0) % 360.0

        return SphericalTrigResult(lat1, lon1, lat2, lon2, haversineDist, bearing, sphericalDist)
    }

    private fun toRad(deg: Double): Double = deg * PI / 180.0
    private fun toDeg(rad: Double): Double = rad * 180.0 / PI
}
