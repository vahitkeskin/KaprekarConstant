package com.example.kaprekar.domain.usecase

import kotlin.math.sqrt

data class KeplerResult(
    val semiMajorAxisAU: Double,
    val eccentricity: Double,
    val orbitalPeriodYears: Double,
    val perihelionDistance: Double,
    val aphelionDistance: Double,
    val perihelionSpeedRatio: Double,
    val aphelionSpeedRatio: Double
)

class CalculateKeplerUseCase {
    operator fun invoke(aAU: Double = 1.0, ecc: Double = 0.0167): KeplerResult {
        val safeA = if (aAU <= 0) 1.0 else aAU
        val safeEcc = ecc.coerceIn(0.0, 0.95)

        // 3rd Law: T^2 = a^3 -> T = a^(3/2) in Earth years & AU
        val periodYears = sqrt(safeA * safeA * safeA)

        // 1st Law: Perihelion r_min = a(1-e), Aphelion r_max = a(1+e)
        val perihelion = safeA * (1.0 - safeEcc)
        val aphelion = safeA * (1.0 + safeEcc)

        // 2nd Law (Equal Areas): v_peri / v_ap = r_ap / r_peri = (1+e)/(1-e)
        val periSpeedRatio = (1.0 + safeEcc) / (1.0 - safeEcc)
        val apSpeedRatio = (1.0 - safeEcc) / (1.0 + safeEcc)

        return KeplerResult(
            semiMajorAxisAU = safeA,
            eccentricity = safeEcc,
            orbitalPeriodYears = periodYears,
            perihelionDistance = perihelion,
            aphelionDistance = aphelion,
            perihelionSpeedRatio = periSpeedRatio,
            aphelionSpeedRatio = apSpeedRatio
        )
    }
}
