package com.example.kaprekar.presentation.ui.nimgame

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.usecase.CalculateNimGameUseCase
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NimGameScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    useCase: CalculateNimGameUseCase = remember { CalculateNimGameUseCase() }
) {
    var heap1 by remember { mutableStateOf(3f) }
    var heap2 by remember { mutableStateOf(4f) }
    var heap3 by remember { mutableStateOf(5f) }

    val heaps = remember(heap1, heap2, heap3) {
        listOf(heap1.toInt(), heap2.toInt(), heap3.toInt())
    }
    val result = remember(heaps) { useCase(heaps) }
    val strings = state.strings

    Scaffold(
        topBar = {
            TopGradientAppBar(
                title = state.strings.topicNimGameTitle,
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
                            text = "Nim Yığınları (Heap Durumları)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text("${strings.labelValue} 1: ${heap1.toInt()}", fontWeight = FontWeight.Bold)
                        Slider(value = heap1, onValueChange = { heap1 = it }, valueRange = 1f..10f)

                        Text("${strings.labelValue} 2: ${heap2.toInt()}", fontWeight = FontWeight.Bold)
                        Slider(value = heap2, onValueChange = { heap2 = it }, valueRange = 1f..10f)

                        Text("${strings.labelValue} 3: ${heap3.toInt()}", fontWeight = FontWeight.Bold)
                        Slider(value = heap3, onValueChange = { heap3 = it }, valueRange = 1f..10f)
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (result.isWinningPosition) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Nim-Sum: ⊕ = ${result.nimSum}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = if (result.isWinningPosition) "⊕ ≠ 0 (Winning)" else "⊕ = 0 (Losing)",
                            fontWeight = FontWeight.SemiBold
                        )
                        result.recommendedMove?.let { move ->
                            Divider()
                            Text(
                                text = "H${move.heapIndex + 1} ➜ -${move.removeAmount}",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NimGameScreenPreview() {
    MaterialTheme {
        NimGameScreen(state = KaprekarUiState(), onIntent = {})
    }
}
