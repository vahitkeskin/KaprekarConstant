package com.example.kaprekar.presentation.ui.transformation

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
import com.example.kaprekar.domain.usecase.CalculateTransformationUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TransformationScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateTransformationUseCase = remember { CalculateTransformationUseCase() }
) {
    var m00 by remember { mutableStateOf(1f) }
    var m01 by remember { mutableStateOf(0.5f) }
    var m10 by remember { mutableStateOf(0f) }
    var m11 by remember { mutableStateOf(1f) }

    val result = remember(m00, m01, m10, m11) {
        useCase(m00.toDouble(), m01.toDouble(), m10.toDouble(), m11.toDouble())
    }

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicTransformationTitle,
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
                            text = "2D Matris Lineer Dönüşümü",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            val pinkColor = MaterialTheme.colorScheme.primary
                            val cyanColor = MaterialTheme.colorScheme.secondary

                            Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                                val center = Offset(size.width / 2f, size.height / 2f)
                                val scale = 18.dp.toPx()

                                // Original Grid Points in Light Gray
                                result.gridVectors.forEach { v ->
                                    val ox = center.x + (v.origX * scale).toFloat()
                                    val oy = center.y - (v.origY * scale).toFloat()
                                    drawCircle(Color.Gray.copy(0.3f), center = Offset(ox, oy), radius = 2.dp.toPx())

                                    val nx = center.x + (v.newX * scale).toFloat()
                                    val ny = center.y - (v.newY * scale).toFloat()
                                    drawCircle(pinkColor, center = Offset(nx, ny), radius = 3.dp.toPx())
                                }

                                // i_hat vector (1,0 -> m00, m10)
                                val ix = center.x + (result.m00 * scale).toFloat()
                                val iy = center.y - (result.m10 * scale).toFloat()
                                drawLine(pinkColor, center, Offset(ix, iy), strokeWidth = 3.dp.toPx())

                                // j_hat vector (0,1 -> m01, m11)
                                val jx = center.x + (result.m01 * scale).toFloat()
                                val jy = center.y - (result.m11 * scale).toFloat()
                                drawLine(cyanColor, center, Offset(jx, jy), strokeWidth = 3.dp.toPx())
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Determinant det(M) = ${(result.determinant * 100).toInt() / 100.0}", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("m00: ${m00.toInt()}", style = MaterialTheme.typography.labelSmall)
                        Slider(value = m00, onValueChange = { m00 = it }, valueRange = -2f..2f)

                        Text("m01 (Shear X): ${m01.toInt()}", style = MaterialTheme.typography.labelSmall)
                        Slider(value = m01, onValueChange = { m01 = it }, valueRange = -2f..2f)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TransformationScreenPreview() {
    MaterialTheme {
        TransformationScreen(state = KaprekarUiState(), onIntent = {})
    }
}
