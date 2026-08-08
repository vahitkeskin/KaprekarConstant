package com.example.kaprekar.presentation.ui.kepler

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
import com.example.kaprekar.domain.usecase.CalculateKeplerUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun KeplerScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateKeplerUseCase = remember { CalculateKeplerUseCase() }
) {
    var aAU by remember { mutableStateOf(1f) }
    var eccentricity by remember { mutableStateOf(0.5f) }

    val result = remember(aAU, eccentricity) { useCase(aAU.toDouble(), eccentricity.toDouble()) }

    val infiniteTransition = rememberInfiniteTransition()
    val orbitAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing), RepeatMode.Restart)
    )

    val strings = state.strings

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = strings.topicKeplerTitle,
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
                        Text("🪐 T² = a³ (${strings.labelResult})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "T = ${round(result.orbitalPeriodYears)} T",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = BrandPink
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${round(result.perihelionDistance)} AU | ${round(result.aphelionDistance)} AU")
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🌌 ${strings.labelSimulation}", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val cx = size.width / 2
                                val cy = size.height / 2
                                val rx = size.width * 0.35f
                                val ry = rx * sqrt(1.0 - (eccentricity * eccentricity).toDouble()).toFloat()

                                val sunX = cx - rx * eccentricity
                                val sunY = cy

                                // Orbit Path
                                drawOval(
                                    color = BrandCyan,
                                    topLeft = Offset(cx - rx, cy - ry),
                                    size = androidx.compose.ui.geometry.Size(rx * 2, ry * 2),
                                    style = Stroke(width = 3.dp.toPx())
                                )

                                // Sun Focus
                                drawCircle(color = Color(0xFFFFB74D), radius = 14.dp.toPx(), center = Offset(sunX, sunY))

                                // Planet Motion along Ellipse
                                val rad = kotlin.math.PI * orbitAngle / 180.0
                                val px = cx + rx * cos(rad).toFloat()
                                val py = cy + ry * sin(rad).toFloat()

                                // Swept Area Triangle
                                val sweepPath = Path().apply {
                                    moveTo(sunX, sunY)
                                    lineTo(px, py)
                                    lineTo(cx + rx * cos(rad - 0.2).toFloat(), cy + ry * sin(rad - 0.2).toFloat())
                                    close()
                                }
                                drawPath(sweepPath, color = BrandPink.copy(alpha = 0.3f))

                                // Planet Node
                                drawCircle(color = BrandPink, radius = 9.dp.toPx(), center = Offset(px, py))
                                drawCircle(color = Color.White, radius = 4.dp.toPx(), center = Offset(px, py))
                            }
                        }
                    }
                }
            }

            item {
                Text("(a): ${round(aAU.toDouble())} AU", fontWeight = FontWeight.Bold)
                Slider(value = aAU, onValueChange = { aAU = it }, valueRange = 0.5f..5f)

                Text("(e): ${round(eccentricity.toDouble())}", fontWeight = FontWeight.Bold)
                Slider(value = eccentricity, onValueChange = { eccentricity = it }, valueRange = 0.0f..0.8f)
            }
        }
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 100.0)) / 100.0

@Preview
@Composable
fun KeplerScreenPreview() {
    MaterialTheme {
        KeplerScreen(state = KaprekarUiState(), onIntent = {})
    }
}
