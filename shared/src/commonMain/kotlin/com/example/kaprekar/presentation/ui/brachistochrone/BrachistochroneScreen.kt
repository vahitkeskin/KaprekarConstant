package com.example.kaprekar.presentation.ui.brachistochrone

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.usecase.CalculateBrachistochroneUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import kotlin.math.sin

@Composable
fun BrachistochroneScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateBrachistochroneUseCase = remember { CalculateBrachistochroneUseCase() }
) {
    var dropH by remember { mutableStateOf(10f) }
    var distD by remember { mutableStateOf(10f) }

    val result = remember(dropH, distD) { useCase(dropH.toDouble(), distD.toDouble()) }

    val infiniteTransition = rememberInfiniteTransition()
    val raceProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing), RepeatMode.Restart)
    )

    val strings = state.strings

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = strings.topicBrachistochroneTitle,
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
                        Text("🏎️ ${strings.labelResult}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${round(result.cycloidTime)} s",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = BrandPink
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${round(result.straightLineTime)} s")
                        Text("Δ = ${round(result.timeDifference)} s")
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🏁 ${strings.labelSimulation}", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val h = size.height

                                val startX = 30f
                                val startY = 30f
                                val endX = w - 30f
                                val endY = h - 30f

                                // Straight Line Track
                                drawLine(color = Color.Gray.copy(alpha = 0.5f), start = Offset(startX, startY), end = Offset(endX, endY), strokeWidth = 3f)

                                // Cycloid Curve Track
                                val cycloidPath = Path().apply {
                                    moveTo(startX, startY)
                                    cubicTo(startX + (endX - startX) * 0.2f, endY, startX + (endX - startX) * 0.7f, endY, endX, endY)
                                }
                                drawPath(cycloidPath, color = BrandPink, style = Stroke(width = 4f))

                                // Straight Line Ball Position
                                val straightBallX = startX + raceProgress * (endX - startX)
                                val straightBallY = startY + raceProgress * (endY - startY)
                                drawCircle(color = Color.Gray, radius = 7.dp.toPx(), center = Offset(straightBallX, straightBallY))

                                // Cycloid Ball Position (Advances Faster in Middle)
                                val cyProgress = (raceProgress * 1.3f).coerceAtMost(1f)
                                val cyBallX = startX + cyProgress * (endX - startX)
                                val cyBallY = startY + (cyProgress * cyProgress) * (endY - startY)
                                drawCircle(color = BrandPink, radius = 9.dp.toPx(), center = Offset(cyBallX, cyBallY))
                                drawCircle(color = Color.White, radius = 4.dp.toPx(), center = Offset(cyBallX, cyBallY))
                            }
                        }
                    }
                }
            }

            item {
                Text("${strings.labelHeight} (h): ${dropH.toInt()} m", fontWeight = FontWeight.Bold)
                Slider(value = dropH, onValueChange = { dropH = it }, valueRange = 2f..50f)

                Text("${strings.labelDistance} (d): ${distD.toInt()} m", fontWeight = FontWeight.Bold)
                Slider(value = distD, onValueChange = { distD = it }, valueRange = 2f..50f)
            }
        }
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 100.0)) / 100.0

@Preview
@Composable
fun BrachistochroneScreenPreview() {
    MaterialTheme {
        BrachistochroneScreen(state = KaprekarUiState(), onIntent = {})
    }
}
