package com.example.kaprekar.domain.usecase

data class FractalPoint(val x: Double, val y: Double, val iterations: Int)

data class FractalResult(val points: List<FractalPoint>, val maxIterations: Int)

class CalculateFractalUseCase {
    operator fun invoke(
        isMandelbrot: Boolean = true,
        maxIterations: Int = 40,
        gridSize: Int = 30,
        cx: Double = -0.7,
        cy: Double = 0.27015
    ): FractalResult {
        val points = mutableListOf<FractalPoint>()

        val xMin = if (isMandelbrot) -2.0 else -1.5
        val xMax = if (isMandelbrot) 1.0 else 1.5
        val yMin = -1.2
        val yMax = 1.2

        val dx = (xMax - xMin) / gridSize
        val dy = (yMax - yMin) / gridSize

        for (i in 0..gridSize) {
            val x0 = xMin + i * dx
            for (j in 0..gridSize) {
                val y0 = yMin + j * dy

                var zx = if (isMandelbrot) 0.0 else x0
                var zy = if (isMandelbrot) 0.0 else y0
                val cRe = if (isMandelbrot) x0 else cx
                val cIm = if (isMandelbrot) y0 else cy

                var iter = 0
                while (zx * zx + zy * zy <= 4.0 && iter < maxIterations) {
                    val xtemp = zx * zx - zy * zy + cRe
                    zy = 2.0 * zx * zy + cIm
                    zx = xtemp
                    iter++
                }

                points.add(FractalPoint(x0, y0, iter))
            }
        }

        return FractalResult(points, maxIterations)
    }
}
