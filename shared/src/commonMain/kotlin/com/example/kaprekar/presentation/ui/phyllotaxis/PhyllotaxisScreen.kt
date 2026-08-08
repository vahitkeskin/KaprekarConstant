package com.example.kaprekar.presentation.ui.phyllotaxis

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
import com.example.kaprekar.domain.usecase.CalculatePhyllotaxisUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PhyllotaxisScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculatePhyllotaxisUseCase = remember { CalculatePhyllotaxisUseCase() }
) {
    var seedCount by remember { mutableStateOf(250f) }
    val result = remember(seedCount) { useCase(seedCount.toInt()) }
    val strings = state.strings

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicPhyllotaxisTitle,
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
                            text = strings.topicPhyllotaxisTitle,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            val pinkColor = MaterialTheme.colorScheme.primary
                            val cyanColor = MaterialTheme.colorScheme.secondary

                            Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                                val center = Offset(size.width / 2f, size.height / 2f)

                                result.seeds.forEach { seed ->
                                    val px = center.x + seed.x.toFloat()
                                    val py = center.y + seed.y.toFloat()
                                    val ratio = seed.index / seedCount
                                    val dotColor = Color(
                                        red = pinkColor.red * (1 - ratio) + cyanColor.red * ratio,
                                        green = pinkColor.green * (1 - ratio) + cyanColor.green * ratio,
                                        blue = pinkColor.blue * (1 - ratio) + cyanColor.blue * ratio,
                                        alpha = 1f
                                    )
                                    drawCircle(dotColor, center = Offset(px, py), radius = 3.5.dp.toPx())
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("${strings.labelCount}: ${seedCount.toInt()}", fontWeight = FontWeight.Bold)
                        Slider(
                            value = seedCount,
                            onValueChange = { seedCount = it },
                            valueRange = 50f..500f,
                            steps = 44
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PhyllotaxisScreenPreview() {
    MaterialTheme {
        PhyllotaxisScreen(state = KaprekarUiState(), onIntent = {})
    }
}
