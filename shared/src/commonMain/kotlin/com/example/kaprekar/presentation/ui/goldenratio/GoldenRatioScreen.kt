package com.example.kaprekar.presentation.ui.goldenratio

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.kaprekar.domain.usecase.CalculateGoldenRatioUseCase
import com.example.kaprekar.domain.usecase.GoldenRatioResult
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun GoldenRatioScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit
) {
    var totalLengthInput by remember { mutableStateOf(100f) }
    val useCase = remember { CalculateGoldenRatioUseCase() }
    val result: GoldenRatioResult = remember(totalLengthInput) {
        useCase.calculateFromTotal(totalLengthInput.toDouble())
    }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFAB47BC).copy(alpha = 0.2f),
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
                    title = state.strings.topicGoldenRatioTitle,
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
                // Main Control Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFAB47BC).copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Toplam Uzunluk (L): ${result.totalLength.toInt()}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )

                            Slider(
                                value = totalLengthInput,
                                onValueChange = { totalLengthInput = it },
                                valueRange = 10f..500f,
                                colors = SliderDefaults.colors(
                                    thumbColor = BrandPink,
                                    activeTrackColor = BrandPink
                                )
                            )

                            // Division Results
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Surface(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    color = BrandPink.copy(alpha = 0.15f),
                                    border = BorderStroke(1.dp, BrandPink.copy(alpha = 0.4f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(state.strings.labelValue, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        Text(
                                            text = "${(result.segmentA * 100).toLong() / 100.0}",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = BrandPink
                                        )
                                    }
                                }

                                Surface(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    color = BrandCyan.copy(alpha = 0.15f),
                                    border = BorderStroke(1.dp, BrandCyan.copy(alpha = 0.4f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(state.strings.labelValue, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        Text(
                                            text = "${(result.segmentB * 100).toLong() / 100.0}",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = BrandCyan
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Visual Line Segment Division
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        ),
                        border = BorderStroke(1.dp, BrandPink.copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Görsel Altın Bölüm Çizgisi",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )

                            // Proportional Bar
                            val aWeight = (result.segmentA / result.totalLength).toFloat()
                            val bWeight = (result.segmentB / result.totalLength).toFloat()

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(28.dp)
                                    .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(14.dp)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(aWeight)
                                        .fillMaxHeight()
                                        .background(BrandPink, RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "A (%${(aWeight * 100).toInt()})",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(bWeight)
                                        .fillMaxHeight()
                                        .background(BrandCyan, RoundedCornerShape(topEnd = 14.dp, bottomEnd = 14.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "B (%${(bWeight * 100).toInt()})",
                                        color = Color.Black,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Text(
                                text = "L / A = A / B = Φ ≈ 1.6180339887...",
                                fontFamily = FontFamily.Monospace,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = BrandPink,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                // Golden Rectangle Visualizer Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        ),
                        border = BorderStroke(1.dp, BrandCyan.copy(alpha = 0.4f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Altın Dikdörtgen Oran Simülasyonu",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )

                            Box(
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(136.dp) // 220 / 1.618 ≈ 136
                                    .background(
                                        Brush.linearGradient(listOf(BrandPink.copy(alpha = 0.3f), BrandCyan.copy(alpha = 0.3f))),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .border(1.5.dp, BrandPink, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "220px × 136px\n(En/Boy = 1.618)",
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
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
fun GoldenRatioScreenPreview() {
    MaterialTheme {
        GoldenRatioScreen(
            state = KaprekarUiState(),
            onIntent = {}
        )
    }
}
