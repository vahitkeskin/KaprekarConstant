package com.example.kaprekar.presentation.ui.cantor

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateCantorUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import kotlin.math.pow

@Composable
fun CantorScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateCantorUseCase = remember { CalculateCantorUseCase() }
) {
    var maxStep by remember { mutableStateOf(5f) }
    val result = remember(maxStep) { useCase(maxStep.toInt()) }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = "Cantor Kümesi & Sonsuzluk (Georg Cantor)",
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
                        Text("♾️ Hausdorff Fraktal Boyutu", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "D = ln(2)/ln(3) ≈ ${round(result.hausdorffDimension)}",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Sonsuz adımda toplam uzunluğu 0 olan ama sayılamaz sonsuzlukta nokta içeren küme!")
                    }
                }
            }

            item {
                Text("Adım Sayısı (n = ${maxStep.toInt()}):", fontWeight = FontWeight.Bold)
                Slider(value = maxStep, onValueChange = { maxStep = it }, valueRange = 1f..6f, steps = 4)
            }

            items(result.steps) { step ->
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Adım ${step.step}: Parça Sayısı = ${step.segmentCount} | Kalan Uzunluk = ${round(step.totalRemainingLength)}", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(20.dp)) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val segs = step.segmentCount
                                drawCantorLine(0f, w, step.step, size.height / 2)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCantorLine(x: Float, width: Float, depth: Int, y: Float) {
    if (depth == 0) {
        drawLine(color = Color(0xFFFF2E93), start = Offset(x, y), end = Offset(x + width, y), strokeWidth = 8f)
    } else {
        val w3 = width / 3f
        drawCantorLine(x, w3, depth - 1, y)
        drawCantorLine(x + 2 * w3, w3, depth - 1, y)
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 10000.0)) / 10000.0
