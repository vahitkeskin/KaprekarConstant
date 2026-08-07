package com.example.kaprekar.presentation.ui.modular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateModularUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ModularScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateModularUseCase = remember { CalculateModularUseCase() }
) {
    var baseInput by remember { mutableStateOf("7") }
    var expInput by remember { mutableStateOf("13") }
    var modInput by remember { mutableStateOf("19") }

    val base = baseInput.toLongOrNull() ?: 7L
    val exp = expInput.toLongOrNull() ?: 13L
    val mod = modInput.toLongOrNull() ?: 19L
    val result = remember(base, exp, mod) { useCase(base, exp, mod) }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicModularTitle,
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
                        Text(
                            text = "aᵇ (mod m) Hesaplayıcı",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = baseInput,
                                onValueChange = { baseInput = it },
                                label = { Text("Taban (a)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = expInput,
                                onValueChange = { expInput = it },
                                label = { Text("Üs (b)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = modInput,
                                onValueChange = { modInput = it },
                                label = { Text("Mod (m)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Sonuç:", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = "${result.base} ^ ${result.exponent} ≡ ${result.modPowResult} (mod ${result.modulus})",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Euler Totient ϕ(${result.modulus}) = ${result.eulerTotient}",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Aralarında Asal Sayılar: ${result.coprimes.take(15).joinToString(", ")}${if (result.coprimes.size > 15) "..." else ""}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ModularScreenPreview() {
    MaterialTheme {
        ModularScreen(state = KaprekarUiState(), onIntent = {})
    }
}
