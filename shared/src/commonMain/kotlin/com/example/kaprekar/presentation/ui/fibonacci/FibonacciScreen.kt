package com.example.kaprekar.presentation.ui.fibonacci

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.usecase.CalculateFibonacciUseCase
import com.example.kaprekar.domain.usecase.FibonacciResult
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun FibonacciScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit
) {
    var termCount by remember { mutableStateOf(15f) }
    val useCase = remember { CalculateFibonacciUseCase() }
    val result: FibonacciResult = remember(termCount) {
        useCase.execute(termCount.toInt())
    }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            BrandCyan.copy(alpha = 0.2f),
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
                    title = state.strings.topicFibonacciTitle,
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Slider & Controls Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        ),
                        border = BorderStroke(1.dp, BrandCyan.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Terim Sayısı (N): ${termCount.toInt()}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = BrandCyan.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "Fn = ${result.value}",
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = BrandCyan,
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            Slider(
                                value = termCount,
                                onValueChange = { termCount = it },
                                valueRange = 2f..40f,
                                steps = 37,
                                colors = SliderDefaults.colors(
                                    thumbColor = BrandCyan,
                                    activeTrackColor = BrandCyan
                                )
                            )

                            // Summary Info
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "Altın Oran Yaklaşımı (Fn / Fn-1):",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                    Text(
                                        text = "≈ ${result.goldenRatioApproximation}",
                                        fontFamily = FontFamily.Monospace,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = BrandPink,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = "Gerçek Altın Oran Φ ≈ 1.6180339887...",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Dizi Elemanları & Oran Yakınşaması",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                itemsIndexed(result.ratioConvergence) { idx, item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                        ),
                        border = BorderStroke(1.dp, BrandPink.copy(alpha = 0.25f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "F${item.index} = ${item.fn}",
                                    fontFamily = FontFamily.Monospace,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "${item.fn} / ${item.fnMinus1}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "${(item.ratio * 1000000).toLong() / 1000000.0}",
                                    fontFamily = FontFamily.Monospace,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = BrandPink,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "Fark: ${item.deltaFromGolden}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 10.sp
                                    )
                                )
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
fun FibonacciScreenPreview() {
    MaterialTheme {
        FibonacciScreen(
            state = KaprekarUiState(),
            onIntent = {}
        )
    }
}
