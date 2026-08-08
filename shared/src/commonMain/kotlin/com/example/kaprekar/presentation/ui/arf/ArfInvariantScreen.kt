package com.example.kaprekar.presentation.ui.arf

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateArfInvariantUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun ArfInvariantScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateArfInvariantUseCase = remember { CalculateArfInvariantUseCase() }
) {
    var coeffA by remember { mutableStateOf(1) }
    var coeffB by remember { mutableStateOf(1) }
    var coeffC by remember { mutableStateOf(1) }

    val result = remember(coeffA, coeffB, coeffC) { useCase(coeffA, coeffB, coeffC) }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = "Arf Değişmezi (Cahit Arf)",
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
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🇹🇷 Cahit Arf (1910 - 1997)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(result.cahitArfInfo, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Kuadratik Form: Q(x, y) = a·x² + b·x·y + c·y² (mod 2)", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Arf(Q) = ${result.arfValue}", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(result.formulaExplanation, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            item {
                Text("Katsayı Seçimleri (0 veya 1):", fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { coeffA = 1 - coeffA }) { Text("a = $coeffA") }
                    Button(onClick = { coeffB = 1 - coeffB }) { Text("b = $coeffB") }
                    Button(onClick = { coeffC = 1 - coeffC }) { Text("c = $coeffC") }
                }
            }
        }
    }
}
