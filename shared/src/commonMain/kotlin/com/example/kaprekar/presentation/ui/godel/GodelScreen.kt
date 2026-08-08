package com.example.kaprekar.presentation.ui.godel

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.usecase.CalculateGodelUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun GodelScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateGodelUseCase = remember { CalculateGodelUseCase() }
) {
    var formulaInput by remember { mutableStateOf("x+y=z") }
    val result = remember(formulaInput) { useCase(formulaInput) }

    val infiniteTransition = rememberInfiniteTransition()
    val pulsePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing), RepeatMode.Restart)
    )

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = "Gödel Sayılaması & Asal Kodlama",
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
                        Text("🔒 Kurt Gödel - Asal Sayı Üs Kodlaması", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "G = ${result.godelNumberRepresentation}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = BrandPink
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("G(s) = 2^a1 × 3^a2 × 5^a3 × 7^a4 ... asal üs çarpımı")
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🌐 Asal Sayı Zirveleri & Üs Ağı", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val count = result.primeFactors.size
                                if (count > 0) {
                                    val stepX = size.width / (count + 1)
                                    val cy = size.height / 2

                                    for (i in 0 until count) {
                                        val factor = result.primeFactors[i]
                                        val cx = stepX * (i + 1)
                                        val r = (12 + (factor.asciiValue % 10)).dp.toPx()

                                        if (i > 0) {
                                            val prevX = stepX * i
                                            drawLine(color = BrandCyan.copy(0.4f), start = Offset(prevX, cy), end = Offset(cx, cy), strokeWidth = 3f)
                                        }

                                        val pulsingR = r + 4f * kotlin.math.sin(pulsePhase + i).toFloat()
                                        drawCircle(color = BrandPink.copy(0.3f), radius = pulsingR, center = Offset(cx, cy))
                                        drawCircle(color = BrandPink, radius = r, center = Offset(cx, cy))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = formulaInput,
                    onValueChange = { formulaInput = it },
                    label = { Text("Matematiksel Sembol / İfade") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Text("Sembol Asal Üs Çarpanları:", fontWeight = FontWeight.Bold)
            }

            items(result.primeFactors) { item ->
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Sembol: '${item.symbolChar}' (ASCII: ${item.asciiValue})", fontWeight = FontWeight.Bold)
                        Text(item.termPower, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun GodelScreenPreview() {
    MaterialTheme {
        GodelScreen(state = KaprekarUiState(), onIntent = {})
    }
}
