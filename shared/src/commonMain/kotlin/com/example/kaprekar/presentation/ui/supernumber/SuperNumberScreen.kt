package com.example.kaprekar.presentation.ui.supernumber

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import com.example.kaprekar.domain.usecase.CalculateSuperNumberUseCase
import com.example.kaprekar.domain.usecase.SuperNumberAnalysisResult
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

@Composable
fun SuperNumberScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit
) {
    var inputStr by remember { mutableStateOf("153") }
    val useCase = remember { CalculateSuperNumberUseCase() }
    val num = inputStr.toLongOrNull() ?: 153L
    val analysisResult: SuperNumberAnalysisResult = remember(num) {
        useCase.execute(num)
    }

    val presets = listOf("153", "28", "495", "18", "25", "370", "407", "76")

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFB74D).copy(alpha = 0.2f),
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
                    title = state.strings.topicSuperNumberTitle,
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
                // Input Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFFFB74D).copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Sayı Analizörü",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )

                            OutlinedTextField(
                                value = inputStr,
                                onValueChange = { inputStr = it.filter { c -> c.isDigit() }.take(7) },
                                label = { Text("İncelenecek Sayı") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = BrandPink,
                                    unfocusedBorderColor = Color(0xFFFFB74D)
                                )
                            )

                            // Preset Row
                            Text(
                                text = "Örnek Sayılar:",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                presets.take(5).forEach { preset ->
                                    FilterChip(
                                        selected = inputStr == preset,
                                        onClick = { inputStr = preset },
                                        label = { Text(preset, fontSize = 12.sp) }
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "$num Sayısının Süper Özellikleri",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                items(analysisResult.properties) { prop ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.88f)
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (prop.isMatched) BrandPink.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.2f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = if (prop.isMatched) BrandPink.copy(alpha = 0.15f) else Color.Gray.copy(alpha = 0.1f),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (prop.isMatched) Icons.Default.Check else Icons.Default.Close,
                                        contentDescription = null,
                                        tint = if (prop.isMatched) BrandPink else Color.Gray
                                    )
                                }
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = prop.propertyName,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (prop.isMatched) BrandPink.copy(alpha = 0.15f) else Color.Gray.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = if (prop.isMatched) "UYUŞUYOR" else "SAĞLAMIYOR",
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (prop.isMatched) BrandPink else Color.Gray
                                        )
                                    }
                                }

                                Text(
                                    text = prop.explanation,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = prop.formulaDetail,
                                    fontFamily = FontFamily.Monospace,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = BrandCyan,
                                        fontWeight = FontWeight.Bold
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
fun SuperNumberScreenPreview() {
    MaterialTheme {
        SuperNumberScreen(
            state = KaprekarUiState(),
            onIntent = {}
        )
    }
}
