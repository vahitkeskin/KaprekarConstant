package com.example.kaprekar.presentation.ui.godel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateGodelUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun GodelScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateGodelUseCase = remember { CalculateGodelUseCase() }
) {
    var formulaInput by remember { mutableStateOf("x+y=z") }
    val result = remember(formulaInput) { useCase(formulaInput) }

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
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("G(s) = 2^a1 × 3^a2 × 5^a3 × 7^a4 ... asal üs çarpımı")
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
                        Text(item.termPower, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
    }
}
