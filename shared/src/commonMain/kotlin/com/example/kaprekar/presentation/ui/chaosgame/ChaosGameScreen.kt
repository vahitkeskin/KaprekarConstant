package com.example.kaprekar.presentation.ui.chaosgame

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateChaosGameUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChaosGameScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateChaosGameUseCase = remember { CalculateChaosGameUseCase() }
) {
    var pointCount by remember { mutableStateOf(1500f) }
    val result = remember(pointCount) { useCase(pointCount.toInt()) }
    val strings = state.strings

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicChaosGameTitle,
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
                            text = "Sierpinski Üçgeni Kaos Oyunu",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .background(Color.Black, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            val cyanColor = MaterialTheme.colorScheme.secondary

                            Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                                val center = Offset(size.width / 2f, size.height / 2f)
                                val scale = size.minDimension * 0.45f

                                result.points.forEach { pt ->
                                    val px = center.x + (pt.x * scale).toFloat()
                                    val py = center.y - (pt.y * scale).toFloat()
                                    drawCircle(cyanColor, center = Offset(px, py), radius = 1.5.dp.toPx())
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("${strings.labelSimulation}: ${pointCount.toInt()}", fontWeight = FontWeight.Bold)
                        Slider(
                            value = pointCount,
                            onValueChange = { pointCount = it },
                            valueRange = 500f..4000f,
                            steps = 34
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChaosGameScreenPreview() {
    MaterialTheme {
        ChaosGameScreen(state = KaprekarUiState(), onIntent = {})
    }
}
