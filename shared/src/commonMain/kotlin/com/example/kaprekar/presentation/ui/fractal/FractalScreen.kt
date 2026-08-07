package com.example.kaprekar.presentation.ui.fractal

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
import com.example.kaprekar.domain.usecase.CalculateFractalUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FractalScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateFractalUseCase = remember { CalculateFractalUseCase() }
) {
    var isMandelbrot by remember { mutableStateOf(true) }
    var maxIter by remember { mutableStateOf(30f) }

    val result = remember(isMandelbrot, maxIter) {
        useCase(isMandelbrot = isMandelbrot, maxIterations = maxIter.toInt(), gridSize = 25)
    }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicFractalTitle,
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
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            FilterChip(
                                selected = isMandelbrot,
                                onClick = { isMandelbrot = true },
                                label = { Text("Mandelbrot Set") }
                            )
                            FilterChip(
                                selected = !isMandelbrot,
                                onClick = { isMandelbrot = false },
                                label = { Text("Julia Set") }
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .background(Color.Black, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            val pinkColor = MaterialTheme.colorScheme.primary
                            val cyanColor = MaterialTheme.colorScheme.secondary

                            Canvas(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                                val w = size.width
                                val h = size.height

                                result.points.forEach { pt ->
                                    val xMin = if (isMandelbrot) -2.0 else -1.5
                                    val xMax = if (isMandelbrot) 1.0 else 1.5
                                    val yMin = -1.2
                                    val yMax = 1.2

                                    val px = ((pt.x - xMin) / (xMax - xMin) * w).toFloat()
                                    val py = ((pt.y - yMin) / (yMax - yMin) * h).toFloat()

                                    val ratio = pt.iterations / result.maxIterations.toFloat()
                                    val pointColor = if (pt.iterations >= result.maxIterations) {
                                        Color.Black
                                    } else {
                                        Color(
                                            red = pinkColor.red * ratio + cyanColor.red * (1 - ratio),
                                            green = pinkColor.green * ratio + cyanColor.green * (1 - ratio),
                                            blue = pinkColor.blue * ratio + cyanColor.blue * (1 - ratio),
                                            alpha = 1f
                                        )
                                    }

                                    drawCircle(pointColor, center = Offset(px, py), radius = 3.dp.toPx())
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Yineleme Sınırı: ${maxIter.toInt()}", fontWeight = FontWeight.Bold)
                        Slider(
                            value = maxIter,
                            onValueChange = { maxIter = it },
                            valueRange = 10f..80f,
                            steps = 14
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FractalScreenPreview() {
    MaterialTheme {
        FractalScreen(state = KaprekarUiState(), onIntent = {})
    }
}
