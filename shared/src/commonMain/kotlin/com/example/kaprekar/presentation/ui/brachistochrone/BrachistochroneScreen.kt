package com.example.kaprekar.presentation.ui.brachistochrone

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateBrachistochroneUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun BrachistochroneScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateBrachistochroneUseCase = remember { CalculateBrachistochroneUseCase() }
) {
    var dropH by remember { mutableStateOf(10f) }
    var distD by remember { mutableStateOf(10f) }

    val result = remember(dropH, distD) { useCase(dropH.toDouble(), distD.toDouble()) }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = "Brachistochrone İniş Eğrisi (Bernoulli)",
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
                        Text("🏎️ En Hızlı İniş Eğrisi (Sikloid)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Sikloid İniş Süresi: ${round(result.cycloidTime)} saniye ⚡",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Düz Çizgi İniş Süresi: ${round(result.straightLineTime)} saniye")
                        Text("Fark (Kazanılan Süre): ${round(result.timeDifference)} saniye daha hızlı!")
                    }
                }
            }

            item {
                Text("Düşme Yüksekliği (h): ${dropH.toInt()} m", fontWeight = FontWeight.Bold)
                Slider(value = dropH, onValueChange = { dropH = it }, valueRange = 2f..50f)

                Text("Yatay Mesafe (d): ${distD.toInt()} m", fontWeight = FontWeight.Bold)
                Slider(value = distD, onValueChange = { distD = it }, valueRange = 2f..50f)
            }
        }
    }
}

private fun round(v: Double): Double = (kotlin.math.round(v * 100.0)) / 100.0
