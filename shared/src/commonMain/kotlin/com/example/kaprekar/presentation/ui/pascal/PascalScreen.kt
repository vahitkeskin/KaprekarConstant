package com.example.kaprekar.presentation.ui.pascal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.usecase.CalculatePascalUseCase
import com.example.kaprekar.domain.usecase.PascalResult
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun PascalScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit
) {
    var rowsInput by remember { mutableStateOf(8f) }
    var nInputStr by remember { mutableStateOf("5") }
    var kInputStr by remember { mutableStateOf("2") }

    val useCase = remember { CalculatePascalUseCase() }
    val nVal = nInputStr.toIntOrNull() ?: 5
    val kVal = kInputStr.toIntOrNull() ?: 2
    val result: PascalResult = remember(rowsInput, nVal, kVal) {
        useCase.execute(rowsInput.toInt(), nVal, kVal)
    }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFF7043).copy(alpha = 0.2f),
            BrandPink.copy(alpha = 0.12f),
            MaterialTheme.colorScheme.surface
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopGradientAppBar(
                    title = state.strings.topicPascalTitle,
                    state = state,
                    onIntent = onIntent,
                    showBackButton = true
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 8.dp,
                    bottom = innerPadding.calculateBottomPadding() + 48.dp
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Combination Calculator Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFFF7043).copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = state.strings.topicPascalTitle,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedTextField(
                                    value = nInputStr,
                                    onValueChange = { nInputStr = it.filter { c -> c.isDigit() }.take(2) },
                                    label = { Text("n") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = kInputStr,
                                    onValueChange = { kInputStr = it.filter { c -> c.isDigit() }.take(2) },
                                    label = { Text("k") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            // Result Badges
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Surface(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    color = BrandPink.copy(alpha = 0.15f)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("C(n,k) Kombinasyon", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        Text(
                                            text = "${result.combinationVal}",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = BrandPink
                                        )
                                    }
                                }

                                Surface(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    color = BrandCyan.copy(alpha = 0.15f)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("P(n,k) Permütasyon", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        Text(
                                            text = "${result.permutationVal}",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = BrandCyan
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Pascal Triangle Rows visualizer
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Pascal Üçgeni Piramidi",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Satır: ${rowsInput.toInt()}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Slider(
                            value = rowsInput,
                            onValueChange = { rowsInput = it },
                            valueRange = 1f..12f,
                            steps = 10,
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFFFF7043),
                                activeTrackColor = Color(0xFFFF7043)
                            )
                        )
                    }
                }

                itemsIndexed(result.rows) { rowIndex, rowList ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            rowList.forEach { valItem ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (valItem == 1L) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) else BrandPink.copy(alpha = 0.15f),
                                    border = BorderStroke(0.5.dp, BrandPink.copy(alpha = 0.3f))
                                ) {
                                    Text(
                                        text = "$valItem",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (valItem == 1L) MaterialTheme.colorScheme.onSurface else BrandPink
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PascalScreenPreview() {
    MaterialTheme {
        PascalScreen(
            state = KaprekarUiState(),
            onIntent = {}
        )
    }
}
