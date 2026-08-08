package com.example.kaprekar.presentation.ui.prime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.kaprekar.domain.usecase.CalculatePrimeUseCase
import com.example.kaprekar.domain.usecase.PrimeResult
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun PrimeScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit
) {
    var inputStr by remember { mutableStateOf("360") }
    val useCase = remember { CalculatePrimeUseCase() }
    val num = inputStr.toLongOrNull() ?: 360L
    val result: PrimeResult = remember(num) {
        useCase.execute(num, sieveLimit = 100)
    }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF29B6F6).copy(alpha = 0.2f),
            BrandPink.copy(alpha = 0.12f),
            MaterialTheme.colorScheme.surface
        )
    )
    val strings = state.strings

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopGradientAppBar(
                    title = state.strings.topicPrimeTitle,
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
                // Input & Factorization Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        ),
                        border = BorderStroke(1.dp, Color(0xFF29B6F6).copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Asallık & Çarpanlara Ayırma",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )

                            OutlinedTextField(
                                value = inputStr,
                                onValueChange = { inputStr = it.filter { c -> c.isDigit() }.take(8) },
                                label = { Text(strings.labelNumberInput) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = BrandPink,
                                    unfocusedBorderColor = Color(0xFF29B6F6)
                                )
                            )

                            // Result Status Banner
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (result.isPrime) BrandPink.copy(alpha = 0.15f) else Color(0xFF29B6F6).copy(alpha = 0.15f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = if (result.isPrime) "$num Bir ASAL SAYIDIR! 🎉" else "$num Asal Değil (Bileşik Sayı)",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            color = if (result.isPrime) BrandPink else Color(0xFF0288D1),
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    )

                                    Text(
                                        text = "Asal Çarpanlar: ${result.factorizationString}",
                                        fontFamily = FontFamily.Monospace,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // Sieve of Eratosthenes Header & Grid
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Eratosthenes Eleği (1-100 Asal Sayılar Grid)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        // Sieve Grid Box
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                            ),
                            border = BorderStroke(1.dp, BrandPink.copy(alpha = 0.3f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(minSize = 34.dp),
                                    modifier = Modifier.height(280.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    items(result.sieveGrid) { sieve ->
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (sieve.isPrime) BrandPink else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                            border = if (sieve.isPrime) null else BorderStroke(0.5.dp, Color.Gray.copy(alpha = 0.3f))
                                        ) {
                                            Box(
                                                modifier = Modifier.size(34.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "${sieve.number}",
                                                    fontFamily = FontFamily.Monospace,
                                                    fontSize = 11.sp,
                                                    fontWeight = if (sieve.isPrime) FontWeight.ExtraBold else FontWeight.Normal,
                                                    color = if (sieve.isPrime) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
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
        }
    }
}

@Preview
@Composable
fun PrimeScreenPreview() {
    MaterialTheme {
        PrimeScreen(
            state = KaprekarUiState(),
            onIntent = {}
        )
    }
}
