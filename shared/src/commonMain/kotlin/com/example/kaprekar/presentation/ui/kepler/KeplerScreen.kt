package com.example.kaprekar.presentation.ui.kepler

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateKeplerUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun KeplerScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateKeplerUseCase = remember { CalculateKeplerUseCase() }
) {
    var aAU by remember { mutableStateOf(1f) }
    var eccentricity by remember { mutableStateOf(0.5f) }

    val result = remember(aAU, eccentricity) { useCase(aAU.toDouble(), eccentricity.toDouble()) }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = "Kepler Yörünge Yasaları",
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
                        Text("🪐 3. Yasa: Yörünge Periyodu (T² = a³)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "T = ${round(result.orbitalPeriodYears)} Yıl",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Günberı (Perihelion): ${round(result.perihelionDistance)} AU | Günöte (Aphelion): ${round(result.aphelionDistance)} AU")
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val cx = size.width / 2
                        val cy = size.height / 2
                        val rx = size.width * 0.35f
                        val ry = rx * (1.0f - eccentricity * 0.5f)

                        // Orbit Ellipse
                        drawOval(
                            color = Color(0xFF00F0FF),
                            topLeft = Offset(cx - rx, cy - ry),
                            size = androidx.compose.ui.geometry.Size(rx * 2, ry * 2),
                            style = Stroke(width = 3.dp.toPx())
                        )

                        // Sun Focus
                        drawCircle(color = Color(0xFFFFB74D), radius = 12.dp.toPx(), center = Offset(cx - rx * eccentricity, cy))

                        // Planet
                        drawCircle(color = Color(0xFFFF2E93), radius = 8.dp.toPx(), center = Offset(cx + rx * cos(1.0).toFloat(), cy + ry * sin(1.0).toFloat()))
                    }
                }
            }

            item {
                Text("Yarı Büyük Eksen (a): ${round(aAU.toDouble())} AU", fontWeight = FontWeight.Bold)
                Slider(value = aAU, onValueChange = { aAU = it }, valueRange = 0.5f..5f)

                Text("Basıklık / Eksantriklik (e): ${round(eccentricity.toDouble())}", fontWeight = FontWeight.Bold)
                Slider(value = eccentricity, onValueChange = { eccentricity = it }, valueRange = 0.0f..0.8f)
            }
        }
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 100.0)) / 100.0
