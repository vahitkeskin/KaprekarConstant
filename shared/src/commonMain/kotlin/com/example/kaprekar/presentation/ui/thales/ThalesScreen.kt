package com.example.kaprekar.presentation.ui.thales

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
import com.example.kaprekar.domain.usecase.CalculateThalesUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun ThalesScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateThalesUseCase = remember { CalculateThalesUseCase() }
) {
    var stickH by remember { mutableStateOf(1.5f) }
    var stickS by remember { mutableStateOf(2.0f) }
    var pyrS by remember { mutableStateOf(196.0f) }

    val result = remember(stickH, stickS, pyrS) { useCase(stickH.toDouble(), stickS.toDouble(), pyrS.toDouble()) }

    val infiniteTransition = rememberInfiniteTransition()
    val sunPulse by infiniteTransition.animateFloat(
        initialValue = 12f,
        targetValue = 18f,
        animationSpec = infiniteRepeatable(tween(1500, easing = FastOutSlowInEasing), RepeatMode.Reverse)
    )

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = "Thales Teoremi & Gölge Hesabı",
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
                        Text("📐 Mısır Piramit Yüksekliği (Thales Yöntemi)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "H = ${round(result.calculatedPyramidHeight)} m",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = BrandPink
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Oran (Çubuk Yükseklik / Gölge): ${round(result.ratio)}")
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("☀️ Güneş Işınları & Dik Üçgen Benzerliği", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val h = size.height

                                // Sun
                                drawCircle(color = Color(0xFFFFB74D), radius = sunPulse.dp.toPx(), center = Offset(w * 0.15f, 30f))

                                // Ground Line
                                drawLine(color = Color.Gray, start = Offset(0f, h - 20f), end = Offset(w, h - 20f), strokeWidth = 3f)

                                // Pyramid Triangle
                                val pyrPath = Path().apply {
                                    moveTo(w * 0.5f, h - 20f)
                                    lineTo(w * 0.7f, h - 120f)
                                    lineTo(w * 0.9f, h - 20f)
                                    close()
                                }
                                drawPath(pyrPath, color = Color(0xFFFFB74D).copy(alpha = 0.3f))
                                drawPath(pyrPath, color = Color(0xFFFFB74D), style = Stroke(width = 3f))

                                // Stick & Shadow Triangle
                                val stickX = w * 0.25f
                                drawLine(color = BrandCyan, start = Offset(stickX, h - 20f), end = Offset(stickX, h - 70f), strokeWidth = 5f)
                                drawLine(color = BrandPink, start = Offset(stickX, h - 20f), end = Offset(stickX + 50f, h - 20f), strokeWidth = 5f)
                                drawLine(color = Color.Yellow.copy(alpha = 0.6f), start = Offset(w * 0.15f, 30f), end = Offset(stickX + 50f, h - 20f), strokeWidth = 2f)
                            }
                        }
                    }
                }
            }

            item {
                Text("Çubuk Yüksekliği: ${round(stickH.toDouble())} m", fontWeight = FontWeight.Bold)
                Slider(value = stickH, onValueChange = { stickH = it }, valueRange = 0.5f..5f)

                Text("Çubuk Gölgesi: ${round(stickS.toDouble())} m", fontWeight = FontWeight.Bold)
                Slider(value = stickS, onValueChange = { stickS = it }, valueRange = 0.5f..10f)

                Text("Piramit Gölge Taban Mesafesi: ${pyrS.toInt()} m", fontWeight = FontWeight.Bold)
                Slider(value = pyrS, onValueChange = { pyrS = it }, valueRange = 10f..400f)
            }
        }
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 100.0)) / 100.0

@Preview
@Composable
fun ThalesScreenPreview() {
    MaterialTheme {
        ThalesScreen(state = KaprekarUiState(), onIntent = {})
    }
}
