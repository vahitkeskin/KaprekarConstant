package com.example.kaprekar.presentation.ui.eratosthenes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateEratosthenesUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
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
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Hesaplanan Yarıçap (R): ${round(result.calculatedRadiusKm)} km")
                        Text("Gerçek Dünya Çevresi: ${round(result.realCircumferenceKm)} km")
                        Text("Doğruluk Oranı: %${round(result.accuracyPercentage)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
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
