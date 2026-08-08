package com.example.kaprekar.presentation.ui.arf

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.usecase.CalculateArfInvariantUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun ArfInvariantScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateArfInvariantUseCase = remember { CalculateArfInvariantUseCase() }
) {
    var coeffA by remember { mutableStateOf(1) }
    var coeffB by remember { mutableStateOf(1) }
    var coeffC by remember { mutableStateOf(1) }

    val result = remember(coeffA, coeffB, coeffC) { useCase(coeffA, coeffB, coeffC) }

    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(8000, easing = LinearEasing), RepeatMode.Restart)
    )

    val strings = state.strings

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = strings.topicArfTitle,
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
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🇹🇷 Cahit Arf (1910 - 1997)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(result.cahitArfInfo, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Q(x, y) = a·x² + b·x·y + c·y² (mod 2)", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Arf(Q) = ${result.arfValue}", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Monospace, color = BrandPink)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(result.formulaExplanation, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🧬 ${strings.labelGraph}", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val cx = size.width / 2
                                val cy = size.height / 2
                                val r = 50.dp.toPx()

                                val path = Path()
                                for (t in 0..360 step 5) {
                                    val rad = kotlin.math.PI * (t + rotationAngle) / 180.0
                                    val scale = 1.0 + 0.2 * kotlin.math.sin(3 * rad)
                                    val px = cx + (r * scale * kotlin.math.cos(rad)).toFloat()
                                    val py = cy + (r * scale * kotlin.math.sin(rad)).toFloat()

                                    if (t == 0) path.moveTo(px, py) else path.lineTo(px, py)
                                }
                                path.close()

                                val nodeColor = if (result.arfValue == 1) BrandPink else BrandCyan
                                drawPath(path, color = nodeColor, style = Stroke(width = 4.dp.toPx()))
                                drawCircle(color = nodeColor.copy(alpha = 0.3f), radius = r * 1.3f, center = Offset(cx, cy))
                            }
                        }
                    }
                }
            }

            item {
                Text("${strings.labelCoefficient}:", fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { coeffA = 1 - coeffA }) { Text("a = $coeffA") }
                    Button(onClick = { coeffB = 1 - coeffB }) { Text("b = $coeffB") }
                    Button(onClick = { coeffC = 1 - coeffC }) { Text("c = $coeffC") }
                }
            }
        }
    }
}

@Preview
@Composable
fun ArfInvariantScreenPreview() {
    MaterialTheme {
        ArfInvariantScreen(state = KaprekarUiState(), onIntent = {})
    }
}
