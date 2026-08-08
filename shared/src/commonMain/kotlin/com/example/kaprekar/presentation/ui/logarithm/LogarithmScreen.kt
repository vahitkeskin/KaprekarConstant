package com.example.kaprekar.presentation.ui.logarithm

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.usecase.CalculateLogarithmUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import kotlin.math.ln
import kotlin.math.log10

@Composable
fun LogarithmScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateLogarithmUseCase = remember { CalculateLogarithmUseCase() }
) {
    var valX by remember { mutableStateOf(100f) }
    var base by remember { mutableStateOf(2f) }
    val result = remember(valX, base) { useCase(valX.toDouble(), base.toDouble()) }

    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing), RepeatMode.Reverse)
    )

    val strings = state.strings

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = strings.topicLogarithmTitle,
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
                        Text("log_b(x) ${strings.labelResult}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "log_${base.toInt()}(${valX.toInt()}) = ${round(result.baseLog)}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = BrandPink
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("ln(x) = ${round(result.naturalLog)}  |  log10(x) = ${round(result.log10Value)}")
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📈 y = log_b(x) ${strings.labelGraph}", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val h = size.height

                                // Axes
                                drawLine(Color.Gray.copy(alpha = 0.3f), Offset(40f, 0f), Offset(40f, h), strokeWidth = 2f)
                                drawLine(Color.Gray.copy(alpha = 0.3f), Offset(0f, h - 30f), Offset(w, h - 30f), strokeWidth = 2f)

                                // Log Curve Path
                                val path = Path()
                                val bVal = base.toDouble()
                                var first = true
                                for (px in 40..w.toInt() step 5) {
                                    val xNorm = (px - 40) / (w - 40) * 100.0 + 1.0
                                    val yVal = ln(xNorm) / ln(bVal)
                                    val py = (h - 30f) - (yVal * (h / 8.0)).toFloat()

                                    if (first) { path.moveTo(px.toFloat(), py); first = false }
                                    else { path.lineTo(px.toFloat(), py) }
                                }

                                drawPath(
                                    path = path,
                                    brush = Brush.horizontalGradient(listOf(BrandCyan, BrandPink)),
                                    style = Stroke(width = 4.dp.toPx())
                                )

                                // Current Point Pulsing Node
                                val currXNorm = valX.toDouble()
                                val currYVal = ln(currXNorm) / ln(bVal)
                                val cx = 40f + ((valX - 1f) / 1000f) * (w - 40f)
                                val cy = (h - 30f) - (currYVal * (h / 8.0)).toFloat()

                                drawCircle(color = BrandPink.copy(alpha = pulseAlpha), radius = 10.dp.toPx(), center = Offset(cx, cy))
                                drawCircle(color = Color.White, radius = 4.dp.toPx(), center = Offset(cx, cy))
                            }
                        }
                    }
                }
            }

            item {
                Text("${strings.labelValue} (x): ${valX.toInt()}", fontWeight = FontWeight.Bold)
                Slider(value = valX, onValueChange = { valX = it }, valueRange = 1f..1000f)
                Text("${strings.labelBase} (b): ${base.toInt()}", fontWeight = FontWeight.Bold)
                Slider(value = base, onValueChange = { base = it }, valueRange = 2f..10f, steps = 7)
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(strings.topicLogarithmTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        HorizontalDivider()
                        Text("🌋 Richter M = ${round(result.richterMagnitude)} Ms")
                        Text("🔊 ${round(result.decibelSound)} dB")
                        Text("🧪 pH = ${round(result.phValue)}")
                    }
                }
            }
        }
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 1000.0)) / 1000.0

@Preview
@Composable
fun LogarithmScreenPreview() {
    MaterialTheme {
        LogarithmScreen(state = KaprekarUiState(), onIntent = {})
    }
}
