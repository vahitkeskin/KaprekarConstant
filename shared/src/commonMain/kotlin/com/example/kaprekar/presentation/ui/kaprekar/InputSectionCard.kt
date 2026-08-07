package com.example.kaprekar.presentation.ui.kaprekar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputSectionCard(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    onCalculate: () -> Unit
) {
    val strings = state.strings
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = strings.inputTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            OutlinedTextField(
                value = state.inputNumber,
                onValueChange = { onIntent(KaprekarUiIntent.OnInputChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(strings.inputLabel) },
                placeholder = { Text(strings.inputPlaceholder) },
                singleLine = true,
                isError = state.validationError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onCalculate() }
                ),
                trailingIcon = {
                    if (state.inputNumber.isNotEmpty()) {
                        IconButton(onClick = { onIntent(KaprekarUiIntent.OnResetClicked) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = strings.resetButton
                            )
                        }
                    }
                },
                supportingText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = state.validationError ?: strings.inputHint,
                            color = if (state.validationError != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "${state.inputNumber.length} / 4",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                },
                shape = RoundedCornerShape(14.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = strings.presetsTitle,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.outline
                    )
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("6825", "3524", "1000", "9876", "1111").forEach { preset ->
                        val isSelected = state.inputNumber == preset
                        val isInvalidPreset = preset == "1111"
                        AssistChip(
                            onClick = { onIntent(KaprekarUiIntent.OnPresetSelected(preset)) },
                            label = {
                                Text(
                                    text = preset,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            leadingIcon = if (isInvalidPreset) {
                                { Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.error) }
                            } else null,
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onCalculate,
                    modifier = Modifier.weight(1f).height(50.dp),
                    enabled = state.isValidInput && !state.isCalculating,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    if (state.isCalculating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(strings.calculatingButton)
                    } else {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = strings.calculateButton,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (state.steps.isNotEmpty() || state.inputNumber.isNotEmpty()) {
                    OutlinedButton(
                        onClick = { onIntent(KaprekarUiIntent.OnResetClicked) },
                        modifier = Modifier.height(50.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = strings.resetButton
                        )
                    }
                }
            }
        }
    }
}
