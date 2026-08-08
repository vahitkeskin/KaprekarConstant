package com.example.kaprekar.presentation.ui.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateStatisticsUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun StatisticsScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateStatisticsUseCase = remember { CalculateStatisticsUseCase() }
) {
    var rawInput by remember { mutableStateOf("12, 15, 18, 20, 22, 25, 28, 30") }

    val numbersList = remember(rawInput) {
        rawInput.split(",", " ", "\n")
            .mapNotNull { it.trim().toDoubleOrNull() }
    }
    val result = remember(numbersList) { useCase(numbersList) }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicStatsTitle,
                state = state,
                onIntent = onIntent,
                showBackButton = true
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = state.strings.labelNumberInput,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = rawInput,
                            onValueChange = { rawInput = it },
                            label = { Text("X") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(state.strings.labelResult, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("N: ${result.count}")
                            Text("Σx: ${result.sum}")
                        }
                        Divider()
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("x̄: ${(result.mean * 100).toInt() / 100.0}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text("Median: ${result.median}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("σ²: ${(result.variance * 100).toInt() / 100.0}")
                            Text("σ: ${(result.stdDev * 100).toInt() / 100.0}", fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Min: ${result.min}")
                            Text("Max: ${result.max}")
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.4f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Gauss Normal Dağılım Çan Eğrisi", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            val curveColor = MaterialTheme.colorScheme.primary
                            val meanColor = MaterialTheme.colorScheme.secondary

                            Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                                val width = size.width
                                val height = size.height
                                val path = Path()

                                val mu = result.mean
                                val sigma = if (result.stdDev == 0.0) 1.0 else result.stdDev

                                val minX = mu - 3 * sigma
                                val maxX = mu + 3 * sigma

                                var first = true
                                for (px in 0..width.toInt() step 4) {
                                    val xVal = minX + (px / width.toDouble()) * (maxX - minX)
                                    val yVal = (1.0 / (sigma * sqrt(2 * kotlin.math.PI))) * exp(-0.5 * ((xVal - mu) / sigma).pow(2))

                                    val py = height - (yVal * height * sigma * 2.28).toFloat()

                                    if (first) {
                                        path.moveTo(px.toFloat(), py.coerceIn(0f, height))
                                        first = false
                                    } else {
                                        path.lineTo(px.toFloat(), py.coerceIn(0f, height))
                                    }
                                }

                                drawPath(path, color = curveColor, style = Stroke(width = 3.dp.toPx()))

                                // Draw mean vertical dashed line in center
                                drawLine(
                                    color = meanColor,
                                    start = Offset(width / 2f, 0f),
                                    end = Offset(width / 2f, height),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StatisticsScreenPreview() {
    MaterialTheme {
        StatisticsScreen(state = KaprekarUiState(), onIntent = {})
    }
}
