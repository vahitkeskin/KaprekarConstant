package com.example.kaprekar.domain.usecase

import kotlin.math.pow
import kotlin.math.sqrt

data class StatisticsResult(
    val count: Int,
    val mean: Double,
    val median: Double,
    val mode: List<Double>,
    val variance: Double,
    val stdDev: Double,
    val min: Double,
    val max: Double,
    val sum: Double,
    val sortedNumbers: List<Double>
)

class CalculateStatisticsUseCase {
    operator fun invoke(numbers: List<Double>): StatisticsResult {
        if (numbers.isEmpty()) {
            return StatisticsResult(0, 0.0, 0.0, emptyList(), 0.0, 0.0, 0.0, 0.0, 0.0, emptyList())
        }

        val sorted = numbers.sorted()
        val count = sorted.size
        val sum = sorted.sum()
        val mean = sum / count

        val median = if (count % 2 == 0) {
            (sorted[count / 2 - 1] + sorted[count / 2]) / 2.0
        } else {
            sorted[count / 2]
        }

        val freqMap = sorted.groupingBy { it }.eachCount()
        val maxFreq = freqMap.values.maxOrNull() ?: 1
        val mode = if (maxFreq > 1) {
            freqMap.filterValues { it == maxFreq }.keys.toList()
        } else {
            emptyList()
        }

        val variance = sorted.sumOf { (it - mean).pow(2) } / count
        val stdDev = sqrt(variance)

        return StatisticsResult(count, mean, median, mode, variance, stdDev, sorted.first(), sorted.last(), sum, sorted)
    }
}
