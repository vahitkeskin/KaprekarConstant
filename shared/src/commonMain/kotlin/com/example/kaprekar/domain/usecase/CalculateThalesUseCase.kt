package com.example.kaprekar.domain.usecase

data class ThalesResult(
    val stickHeight: Double,
    val stickShadow: Double,
    val pyramidShadow: Double,
    val calculatedPyramidHeight: Double,
    val ratio: Double,
    val isRightAngleInCircle: Boolean
)

class CalculateThalesUseCase {
    operator fun invoke(
        stickHeight: Double = 1.5,
        stickShadow: Double = 2.0,
        pyramidShadow: Double = 196.0
    ): ThalesResult {
        val safeStickShadow = if (stickShadow <= 0) 1.0 else stickShadow
        val ratio = stickHeight / safeStickShadow
        val pyramidHeight = ratio * pyramidShadow

        return ThalesResult(
            stickHeight = stickHeight,
            stickShadow = stickShadow,
            pyramidShadow = pyramidShadow,
            calculatedPyramidHeight = pyramidHeight,
            ratio = ratio,
            isRightAngleInCircle = true
        )
    }
}
