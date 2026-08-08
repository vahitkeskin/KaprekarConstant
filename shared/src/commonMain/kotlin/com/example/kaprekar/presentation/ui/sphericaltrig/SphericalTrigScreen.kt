package com.example.kaprekar.presentation.ui.sphericaltrig

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
import com.example.kaprekar.domain.usecase.CalculateSphericalTrigUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun SphericalTrigScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateSphericalTrigUseCase = remember { CalculateSphericalTrigUseCase() }
) {
    var lat1 by remember { mutableStateOf(41.0f) } // Istanbul
    var lon1 by remember { mutableStateOf(28.9f) }
    var lat2 by remember { mutableStateOf(51.5f) } // London
    var lon2 by remember { mutableStateOf(-0.1f) }

    val result = remember(lat1, lon1, lat2, lon2) {
        useCase(lat1.toDouble(), lon1.toDouble(), lat2.toDouble(), lon2.toDouble())
    }

    val infiniteTransition = rememberInfiniteTransition()
    val pulseProgress by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Reverse)
    )

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = "Küresel Trigonometri (El-Battani)",
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
                        Text("✈️ Haversine & Küresel Kosinüs Yasası", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "En Kısa Uçuş Mesafesi: ${round(result.distanceKm)} km",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = BrandPink
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Başlangıç Rotası (Açı): ${round(result.initialBearingDeg)}°")
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🌐 Küre Üzerinde Büyük Daire Rota Çizimi", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val cx = size.width / 2
                                val cy = size.height / 2
                                val r = 60.dp.toPx()

                                drawCircle(color = BrandCyan.copy(0.4f), radius = r, center = Offset(cx, cy), style = Stroke(width = 3.dp.toPx()))

                                val p1x = cx + r * kotlin.math.cos(lat1 * Math.PI / 180).toFloat() * 0.7f
                                val p1y = cy - r * kotlin.math.sin(lat1 * Math.PI / 180).toFloat() * 0.7f

                                val p2x = cx + r * kotlin.math.cos(lat2 * Math.PI / 180).toFloat() * 0.7f
                                val p2y = cy - r * kotlin.math.sin(lat2 * Math.PI / 180).toFloat() * 0.7f

                                val routePath = Path().apply {
                                    moveTo(p1x, p1y)
                                    quadraticTo(cx, cy - r * 1.1f, p2x, p2y)
                                }
                                drawPath(routePath, color = BrandPink, style = Stroke(width = 4.dp.toPx()))

                                drawCircle(color = BrandPink, radius = 7.dp.toPx(), center = Offset(p1x, p1y))
                                drawCircle(color = Color.Yellow, radius = 7.dp.toPx(), center = Offset(p2x, p2y))
                            }
                        }
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Şehir 1 Koordinatı (Enlem: ${round(lat1.toDouble())}°, Boylam: ${round(lon1.toDouble())}°)", fontWeight = FontWeight.Bold)
                        Slider(value = lat1, onValueChange = { lat1 = it }, valueRange = -90f..90f)
                        Slider(value = lon1, onValueChange = { lon1 = it }, valueRange = -180f..180f)
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Şehir 2 Koordinatı (Enlem: ${round(lat2.toDouble())}°, Boylam: ${round(lon2.toDouble())}°)", fontWeight = FontWeight.Bold)
                        Slider(value = lat2, onValueChange = { lat2 = it }, valueRange = -90f..90f)
                        Slider(value = lon2, onValueChange = { lon2 = it }, valueRange = -180f..180f)
                    }
                }
            }
        }
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 10.0)) / 10.0

@Preview
@Composable
fun SphericalTrigScreenPreview() {
    MaterialTheme {
        SphericalTrigScreen(state = KaprekarUiState(), onIntent = {})
    }
}
