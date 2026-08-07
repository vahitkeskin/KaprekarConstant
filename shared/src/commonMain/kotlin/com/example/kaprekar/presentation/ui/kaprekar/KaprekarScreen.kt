package com.example.kaprekar.presentation.ui.kaprekar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.model.KaprekarStep
import com.example.kaprekar.domain.model.ThemeMode
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.KaprekarViewModel

@Composable
fun KaprekarScreen(
    viewModel: KaprekarViewModel
) {
    val state by viewModel.uiState.collectAsState()
    KaprekarContent(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaprekarContent(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    val strings = state.strings

    LaunchedEffect(state.visibleStepCount) {
        if (state.visibleStepCount > 0) {
            val targetIndex = 2 + state.visibleStepCount
            listState.animateScrollToItem(
                index = targetIndex,
                scrollOffset = -120
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "6174",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = strings.appTitle,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Surface(
                            onClick = { onIntent(KaprekarUiIntent.OnToggleLanguageDialog(true)) },
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = state.appLanguage.flagEmoji,
                                    fontSize = 18.sp
                                )
                            }
                        }

                        Surface(
                            onClick = { onIntent(KaprekarUiIntent.OnToggleThemeMode) },
                            shape = CircleShape,
                            color = when (state.themeMode) {
                                ThemeMode.SYSTEM -> MaterialTheme.colorScheme.surfaceVariant
                                ThemeMode.LIGHT -> Color(0xFFFFF3E0)
                                ThemeMode.DARK -> Color(0xFF263238)
                            },
                            border = BorderStroke(
                                width = 1.5.dp,
                                color = when (state.themeMode) {
                                    ThemeMode.SYSTEM -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                                    ThemeMode.LIGHT -> Color(0xFFFFB74D)
                                    ThemeMode.DARK -> Color(0xFF90CAF9)
                                }
                            ),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                val (icon, tint, desc) = when (state.themeMode) {
                                    ThemeMode.SYSTEM -> Triple(
                                        Icons.Default.SettingsBrightness,
                                        MaterialTheme.colorScheme.onSurfaceVariant,
                                        strings.systemTheme
                                    )
                                    ThemeMode.LIGHT -> Triple(
                                        Icons.Default.LightMode,
                                        Color(0xFFF57C00),
                                        strings.lightTheme
                                    )
                                    ThemeMode.DARK -> Triple(
                                        Icons.Default.DarkMode,
                                        Color(0xFF90CAF9),
                                        strings.darkTheme
                                    )
                                }
                                Icon(
                                    imageVector = icon,
                                    contentDescription = desc,
                                    tint = tint,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                        )
                    )
                )
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    HeaderBannerCard(
                        state = state,
                        onInfoClick = { onIntent(KaprekarUiIntent.OnToggleInfoDialog(true)) }
                    )
                }

                item {
                    InputSectionCard(
                        state = state,
                        onIntent = onIntent,
                        onCalculate = {
                            keyboardController?.hide()
                            onIntent(KaprekarUiIntent.OnCalculateClicked)
                        }
                    )
                }

                if (state.steps.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = strings.stepBreakdownTitle,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "${state.visibleStepCount} / ${state.steps.size} ${strings.stepLabel}",
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }

                    val visibleSteps = state.steps.take(state.visibleStepCount)
                    itemsIndexed(visibleSteps) { index, step ->
                        AnimatedStepCard(
                            state = state,
                            step = step,
                            isLastStep = index == state.steps.size - 1 && state.reachedConstant
                        )
                    }

                    if (state.isCompleted && state.reachedConstant) {
                        item {
                            SuccessBannerCard(state = state, totalSteps = state.steps.size)
                        }
                    }
                }
            }
        }
    }

    if (state.showInfoDialog) {
        KaprekarInfoDialog(
            state = state,
            onDismiss = { onIntent(KaprekarUiIntent.OnToggleInfoDialog(false)) }
        )
    }

    if (state.showLanguageDialog) {
        LanguageSelectionDialog(
            state = state,
            onSelect = { onIntent(KaprekarUiIntent.OnSelectLanguage(it)) },
            onDismiss = { onIntent(KaprekarUiIntent.OnToggleLanguageDialog(false)) }
        )
    }
}

@Preview
@Composable
fun KaprekarScreenPreview() {
    val sampleState = KaprekarUiState(
        inputNumber = "3524",
        steps = listOf(
            KaprekarStep(
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
            ),
            KaprekarStep(
                stepNumber = 2,
                inputNumber = "3087",
                descending = "8730",
                ascending = "0378",
                descendingValue = 8730,
                ascendingValue = 378,
                resultValue = 8352,
                resultString = "8352",
                formula = "8730 - 0378 = 8352",
                isKaprekarConstant = false
            ),
            KaprekarStep(
                stepNumber = 3,
                inputNumber = "8352",
                descending = "8532",
                ascending = "2358",
                descendingValue = 8532,
                ascendingValue = 2358,
                resultValue = 6174,
                resultString = "6174",
                formula = "8532 - 2358 = 6174",
                isKaprekarConstant = true
            )
        ),
        visibleStepCount = 3,
        isCompleted = true,
        reachedConstant = true
    )
    MaterialTheme {
        KaprekarContent(
            state = sampleState,
            onIntent = {}
        )
    }
}
