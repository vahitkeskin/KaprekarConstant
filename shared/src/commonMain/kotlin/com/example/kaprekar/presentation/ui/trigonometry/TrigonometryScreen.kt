package com.example.kaprekar.presentation.ui.trigonometry

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
import com.example.kaprekar.domain.usecase.CalculateTrigonometryUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TrigonometryScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateTrigonometryUseCase = remember { CalculateTrigonometryUseCase() }
) {
    var degreeAngle by remember { mutableStateOf(45f) }
    val result = remember(degreeAngle) { useCase(degreeAngle.toDouble()) }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicTrigTitle,
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
                            text = state.strings.topicTrigTitle,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            val primaryColor = MaterialTheme.colorScheme.primary
                            val secondaryColor = MaterialTheme.colorScheme.secondary

                            Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                                val center = Offset(size.width / 2f, size.height / 2f)
                                val radius = size.minDimension / 2f - 10.dp.toPx()

                                // Axes
                                drawLine(Color.Gray.copy(0.4f), Offset(0f, center.y), Offset(size.width, center.y), strokeWidth = 2.dp.toPx())
                                drawLine(Color.Gray.copy(0.4f), Offset(center.x, 0f), Offset(center.x, size.height), strokeWidth = 2.dp.toPx())

                                // Unit circle
                                drawCircle(Color.Gray.copy(0.6f), center = center, radius = radius, style = Stroke(2.dp.toPx()))

                                // Point on circle
                                val rad = result.radians
                                val px = center.x + (radius * result.cosVal).toFloat()
                                val py = center.y - (radius * result.sinVal).toFloat()

                                // Cosine line (horizontal)
                                drawLine(primaryColor, center, Offset(px, center.y), strokeWidth = 4.dp.toPx())
                                // Sine line (vertical)
                                drawLine(secondaryColor, Offset(px, center.y), Offset(px, py), strokeWidth = 4.dp.toPx())
                                // Radius line
                                drawLine(Color.DarkGray, center, Offset(px, py), strokeWidth = 2.dp.toPx())

                                // Point dot
                                drawCircle(primaryColor, center = Offset(px, py), radius = 6.dp.toPx())
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "${state.strings.labelAngle} (θ): ${degreeAngle.toInt()}° (${(result.radians * 100).toInt() / 100.0} rad)",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Slider(
                            value = degreeAngle,
                            onValueChange = { degreeAngle = it },
                            valueRange = 0f..360f,
                            steps = 359
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
                            Text("sin(θ) = ${(result.sinVal * 10000).toInt() / 10000.0}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                            Text("cos(θ) = ${(result.cosVal * 10000).toInt() / 10000.0}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("tan(θ) = ${result.tanVal?.let { (it * 10000).toInt() / 10000.0 } ?: "∞"}")
                            Text("cot(θ) = ${result.cotVal?.let { (it * 10000).toInt() / 10000.0 } ?: "∞"}")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TrigonometryScreenPreview() {
    MaterialTheme {
        TrigonometryScreen(state = KaprekarUiState(), onIntent = {})
    }
}
