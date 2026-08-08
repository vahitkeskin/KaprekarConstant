package com.example.kaprekar.presentation.ui.logarithm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateLogarithmUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun LogarithmScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateLogarithmUseCase = remember { CalculateLogarithmUseCase() }
) {
    var valX by remember { mutableStateOf(100f) }
    var base by remember { mutableStateOf(2f) }
    val result = remember(valX, base) { useCase(valX.toDouble(), base.toDouble()) }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = "Logaritma & Logaritmik Ölçekler",
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
                        Text("log_b(x) Hesabı", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "log_${base.toInt()}(${valX.toInt()}) = ${round(result.baseLog)}",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Doğal Logaritma ln(x) = ${round(result.naturalLog)}  |  log10(x) = ${round(result.log10Value)}")
                    }
                }
            }

            item {
                Text("x Değeri: ${valX.toInt()}", fontWeight = FontWeight.Bold)
                Slider(value = valX, onValueChange = { valX = it }, valueRange = 1f..1000f)
                Text("Taban (b): ${base.toInt()}", fontWeight = FontWeight.Bold)
                Slider(value = base, onValueChange = { base = it }, valueRange = 2f..10f, steps = 7)
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Gerçek Dünya Logaritmik Ölçekleri", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        HorizontalDivider()
                        Text("🌋 Richter Deprem Şiddeti: M = ${round(result.richterMagnitude)} Ms")
                        Text("🔊 Ses Şiddeti (Desibel): ${round(result.decibelSound)} dB")
                        Text("🧪 Kimyasal pH Değeri: pH = ${round(result.phValue)}")
                    }
                }
            }
        }
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 1000.0)) / 1000.0
