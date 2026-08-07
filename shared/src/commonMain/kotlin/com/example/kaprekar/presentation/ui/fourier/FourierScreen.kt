package com.example.kaprekar.presentation.ui.fourier

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateFourierUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FourierScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateFourierUseCase = remember { CalculateFourierUseCase() }
) {
    var harmonicsCount by remember { mutableStateOf(5f) }
    val result = remember(harmonicsCount) { useCase(harmonicsCount.toInt(), "SQUARE") }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicFourierTitle,
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
                            text = "Fourier Kare Dalga Harmonikleri",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            val pinkColor = MaterialTheme.colorScheme.primary

                            Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                                val w = size.width
                                val h = size.height
                                val centerY = h / 2f

                                val path = Path()
                                var first = true

                                result.wavePoints.forEachIndexed { index, yVal ->
                                    val px = (index / result.wavePoints.size.toFloat()) * w
                                    val py = centerY - (yVal * h * 0.35f).toFloat()

                                    if (first) {
                                        path.moveTo(px, py)
                                        first = false
                                    } else {
                                        path.lineTo(px, py)
                                    }
                                }

                                drawPath(path, color = pinkColor, style = Stroke(3.dp.toPx()))
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Harmonik Sayısı (n): ${harmonicsCount.toInt()}", fontWeight = FontWeight.Bold)
                        Slider(
                            value = harmonicsCount,
                            onValueChange = { harmonicsCount = it },
                            valueRange = 1f..15f,
                            steps = 13
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FourierScreenPreview() {
    MaterialTheme {
        FourierScreen(state = KaprekarUiState(), onIntent = {})
    }
}
