package com.example.kaprekar.presentation.ui.kaprekar

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.model.KaprekarStep
import com.example.kaprekar.presentation.KaprekarUiState

@Composable
fun AnimatedStepCard(
    state: KaprekarUiState,
    step: KaprekarStep,
    isLastStep: Boolean
) {
    val strings = state.strings
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(600)) + slideInVertically(
            initialOffsetY = { it / 2 },
            animationSpec = tween(600)
        )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            border = BorderStroke(
                width = if (isLastStep) 2.dp else 1.dp,
                color = if (isLastStep) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isLastStep)
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                else
                    MaterialTheme.colorScheme.surface
            )
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
                    Surface(
                        color = if (isLastStep) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${strings.stepLabel} ${step.stepNumber}",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isLastStep) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }

                    if (step.isKaprekarConstant) {
                        Surface(
                            color = Color(0xFF2E7D32),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = strings.targetBadge,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DigitBadge(label = strings.digitInput, value = step.inputNumber)
                    Text("➜", color = MaterialTheme.colorScheme.outline, modifier = Modifier.align(Alignment.CenterVertically))
                    DigitBadge(label = strings.digitDescending, value = step.descending)
                    DigitBadge(label = strings.digitAscending, value = step.ascending)
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${step.descending} - ${step.ascending} = ",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Text(
                            text = step.resultString,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Black,
                                color = if (step.isKaprekarConstant) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AnimatedStepCardPreview() {
    val sampleStep = KaprekarStep(
        stepNumber = 1,
        inputNumber = "3524",
        descending = "5432",
        ascending = "2345",
        descendingValue = 5432,
        ascendingValue = 2345,
        resultValue = 3087,
        resultString = "3087",
        formula = "5432 - 2345 = 3087",
        isKaprekarConstant = false
    )
    MaterialTheme {
        AnimatedStepCard(
            state = KaprekarUiState(),
            step = sampleStep,
            isLastStep = false
        )
    }
}
