package com.example.kaprekar.presentation.ui.quadratic

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
import com.example.kaprekar.domain.usecase.CalculateQuadraticUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun QuadraticScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateQuadraticUseCase = remember { CalculateQuadraticUseCase() }
) {
    var aInput by remember { mutableStateOf("1") }
    var bInput by remember { mutableStateOf("-5") }
    var cInput by remember { mutableStateOf("6") }

    val a = aInput.toDoubleOrNull() ?: 1.0
    val b = bInput.toDoubleOrNull() ?: -5.0
    val c = cInput.toDoubleOrNull() ?: 6.0
    val result = remember(a, b, c) { useCase(a, b, c) }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicQuadraticTitle,
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
                            text = state.strings.labelCoefficient,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = aInput,
                                onValueChange = { aInput = it },
                                label = { Text("a") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = bInput,
                                onValueChange = { bInput = it },
                                label = { Text("b") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = cInput,
                                onValueChange = { cInput = it },
                                label = { Text("c") },
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
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Δ = b² - 4ac",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Δ = ${result.discriminant}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (result.discriminant > 0) MaterialTheme.colorScheme.primary else if (result.discriminant == 0.0) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
                        )
                        Divider()
                        Text(
                            text = "Kök 1 (x₁): ${result.root1Str}",
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Kök 2 (x₂): ${result.root2Str}",
                            fontWeight = FontWeight.SemiBold
                        )
                        Divider()
                        Text(
                            text = "Parabol Tepe Noktası T(r,k): (${(result.vertexX * 100).toInt() / 100.0}, ${(result.vertexY * 100).toInt() / 100.0})",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun QuadraticScreenPreview() {
    MaterialTheme {
        QuadraticScreen(state = KaprekarUiState(), onIntent = {})
    }
}
