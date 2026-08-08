package com.example.kaprekar.presentation.ui.thales

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateThalesUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
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
                            text = "H = ${round(result.calculatedPyramidHeight)} metre",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Oran (Çubuk Yükseklik / Gölge): ${round(result.ratio)}")
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
