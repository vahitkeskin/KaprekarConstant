package com.example.kaprekar.presentation.ui.eratosthenes

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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.usecase.CalculateEratosthenesUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun EratosthenesScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateEratosthenesUseCase = remember { CalculateEratosthenesUseCase() }
) {
    var angleDeg by remember { mutableStateOf(7.2f) }
    var distKm by remember { mutableStateOf(800f) }

    val result = remember(angleDeg, distKm) { useCase(angleDeg.toDouble(), distKm.toDouble()) }

    val infiniteTransition = rememberInfiniteTransition()
    val earthRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(12000, easing = LinearEasing), RepeatMode.Restart)
    )

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = "Eratosthenes & Dünya Çevre Hesabı",
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
                        Text("🌍 Antik Çağda Dünya'nın Çevresini Ölçmek", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Hesaplanan Çevre: ${round(result.calculatedCircumferenceKm)} km",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = BrandPink
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Hesaplanan Yarıçap (R): ${round(result.calculatedRadiusKm)} km")
                        Text("Gerçek Dünya Çevresi: ${round(result.realCircumferenceKm)} km")
                        Text("Doğruluk Oranı: %${round(result.accuracyPercentage)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("☀️ İskenderiye & Syene Güneş Işını Açısı (θ)", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val cx = size.width / 2
                                val cy = size.height / 2
                                val r = 60.dp.toPx()

                                // Earth Circle
                                drawCircle(color = BrandCyan, radius = r, center = Offset(cx, cy), style = Stroke(width = 3.dp.toPx()))

                                // Parallel Sun Rays
                                drawLine(color = Color(0xFFFFB74D), start = Offset(cx - r * 1.8f, cy - 40f), end = Offset(cx + r * 1.8f, cy - 40f), strokeWidth = 2f)
                                drawLine(color = Color(0xFFFFB74D), start = Offset(cx - r * 1.8f, cy + 40f), end = Offset(cx + r * 1.8f, cy + 40f), strokeWidth = 2f)

                                // Alexandria Obelisk Shadow Angle
                                val rad = kotlin.math.PI * (angleDeg / 180.0)
                                val obeliskX = cx + r * kotlin.math.cos(rad).toFloat()
                                val obeliskY = cy - r * kotlin.math.sin(rad).toFloat()

                                drawCircle(color = BrandPink, radius = 6.dp.toPx(), center = Offset(obeliskX, obeliskY))
                                drawLine(color = BrandPink, start = Offset(cx, cy), end = Offset(obeliskX, obeliskY), strokeWidth = 3f)
                            }
                        }
                    }
                }
            }

            item {
                Text("Gölge Açı Farkı (θ): ${round(angleDeg.toDouble())}°", fontWeight = FontWeight.Bold)
                Slider(value = angleDeg, onValueChange = { angleDeg = it }, valueRange = 1f..15f)

                Text("Şehirler Arası Mesafe (s): ${distKm.toInt()} km", fontWeight = FontWeight.Bold)
                Slider(value = distKm, onValueChange = { distKm = it }, valueRange = 100f..2000f)
            }
        }
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 10.0)) / 10.0

@Preview
@Composable
fun EratosthenesScreenPreview() {
    MaterialTheme {
        EratosthenesScreen(state = KaprekarUiState(), onIntent = {})
    }
}
