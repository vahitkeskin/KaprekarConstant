package com.example.kaprekar.presentation.ui.pi

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculatePiUseCase
import com.example.kaprekar.domain.usecase.PiResult
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PiScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculatePiUseCase = remember { CalculatePiUseCase() }
) {
    var pointCount by remember { mutableStateOf(1000f) }
    var result by remember(pointCount) { mutableStateOf(useCase(pointCount.toInt())) }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicPiTitle,
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
                            text = "Monte Carlo π Görselleştirmesi",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Kare içine rasgele nokta atarak çemberin içine düşen oran: π ≈ 4 × (İçteki / Toplam)",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            val pinkColor = MaterialTheme.colorScheme.primary
                            val cyanColor = MaterialTheme.colorScheme.secondary

                            Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                                val radius = size.minDimension / 2f
                                val center = Offset(size.width / 2f, size.height / 2f)

                                // Square boundary
                                drawRect(
                                    color = Color.Gray.copy(alpha = 0.4f),
                                    topLeft = Offset(center.x - radius, center.y - radius),
                                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                                    style = Stroke(width = 2.dp.toPx())
                                )

                                // Circle boundary
                                drawCircle(
                                    color = pinkColor,
                                    center = center,
                                    radius = radius,
                                    style = Stroke(width = 2.dp.toPx())
                                )

                                // Draw sample points
                                result.samplePoints.forEach { pt ->
                                    val px = center.x + (pt.x * radius).toFloat()
                                    val py = center.y + (pt.y * radius).toFloat()
                                    drawCircle(
                                        color = if (pt.isInside) pinkColor else cyanColor,
                                        center = Offset(px, py),
                                        radius = 3.dp.toPx()
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Nokta Sayısı: ${pointCount.toInt()}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Slider(
                            value = pointCount,
                            onValueChange = { pointCount = it },
                            valueRange = 100f..5000f,
                            steps = 49
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Tahmini π Değeri",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "π ≈ ${result.estimatedPi}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Gerçek π = 3.1415926535...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PiScreenPreview() {
    MaterialTheme {
        PiScreen(state = KaprekarUiState(), onIntent = {})
    }
}
