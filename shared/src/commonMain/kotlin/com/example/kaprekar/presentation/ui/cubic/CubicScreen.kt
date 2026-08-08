package com.example.kaprekar.presentation.ui.cubic

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.kaprekar.domain.usecase.CalculateCubicUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun CubicScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateCubicUseCase = remember { CalculateCubicUseCase() }
) {
    var coeffA by remember { mutableStateOf(1f) }
    var coeffB by remember { mutableStateOf(-6f) }
    var coeffC by remember { mutableStateOf(11f) }
    var coeffD by remember { mutableStateOf(-6f) }

    val result = remember(coeffA, coeffB, coeffC, coeffD) {
        useCase(coeffA.toDouble(), coeffB.toDouble(), coeffC.toDouble(), coeffD.toDouble())
    }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = "3. Derece Denklem (Cardano - Tartaglia)",
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
                        Text("🧩 a·x³ + b·x² + c·x + d = 0", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${coeffA.toInt()}x³ + (${coeffB.toInt()})x² + (${coeffC.toInt()})x + (${coeffD.toInt()}) = 0",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = BrandPink
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("İndirgenmiş Kübik: t³ + (${round(result.p)})t + (${round(result.q)}) = 0")
                        Text("Diskriminant Δ = ${round(result.discriminant)}")
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📈 Canlı Kübik Eğri Grafiği", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val h = size.height

                                drawLine(Color.Gray.copy(0.3f), Offset(0f, h / 2), Offset(w, h / 2), strokeWidth = 2f)
                                drawLine(Color.Gray.copy(0.3f), Offset(w / 2, 0f), Offset(w / 2, h), strokeWidth = 2f)

                                val path = Path()
                                var first = true
                                for (px in 0..w.toInt() step 4) {
                                    val xVal = (px - w / 2) / 25.0
                                    val yVal = coeffA * xVal * xVal * xVal + coeffB * xVal * xVal + coeffC * xVal + coeffD
                                    val py = (h / 2) - (yVal * 10.0).toFloat()

                                    if (py in -100f..(h + 100f)) {
                                        if (first) { path.moveTo(px.toFloat(), py); first = false }
                                        else { path.lineTo(px.toFloat(), py) }
                                    }
                                }
                                drawPath(path, color = BrandCyan, style = Stroke(width = 3.dp.toPx()))
                            }
                        }
                    }
                }
            }

            item {
                Text("Denklem Kökleri (Cardano Formülü):", fontWeight = FontWeight.Bold)
            }

            items(result.roots) { rootStr ->
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Text(
                        text = rootStr,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            item {
                Text("Katsayı Ayarları:", fontWeight = FontWeight.Bold)
                Text("a = ${coeffA.toInt()}")
                Slider(value = coeffA, onValueChange = { coeffA = it }, valueRange = -5f..5f)
                Text("b = ${coeffB.toInt()}")
                Slider(value = coeffB, onValueChange = { coeffB = it }, valueRange = -10f..10f)
                Text("c = ${coeffC.toInt()}")
                Slider(value = coeffC, onValueChange = { coeffC = it }, valueRange = -15f..15f)
                Text("d = ${coeffD.toInt()}")
                Slider(value = coeffD, onValueChange = { coeffD = it }, valueRange = -15f..15f)
            }
        }
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 100.0)) / 100.0

@Preview
@Composable
fun CubicScreenPreview() {
    MaterialTheme {
        CubicScreen(state = KaprekarUiState(), onIntent = {})
    }
}
