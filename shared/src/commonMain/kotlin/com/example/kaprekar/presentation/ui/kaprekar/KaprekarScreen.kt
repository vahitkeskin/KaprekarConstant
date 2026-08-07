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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.model.KaprekarStep
import com.example.kaprekar.domain.model.ThemeMode
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.KaprekarViewModel
import kotlinx.coroutines.delay

val BrandPink = Color(0xFFFF2E93)
val BrandCyan = Color(0xFF00F0FF)

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

@Composable
fun KaprekarContent(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    val strings = state.strings

    // Yavaş animasyonlu adım kaydırma: Yeni adım açıldığında Compose'un çizmesini bekleyip ortalar
    LaunchedEffect(state.visibleStepCount) {
        if (state.visibleStepCount > 0) {
            delay(120) // Yeni kartın Compose tarafından ölçülüp yerleşmesini bekler
            val targetIndex = 2 + state.visibleStepCount
            if (targetIndex < listState.layoutInfo.totalItemsCount) {
                listState.animateScrollToItem(
                    index = targetIndex,
                    scrollOffset = -100
                )
            }
        }
    }

    // Statusbar ve TopBar geçişini sağlayan BrandPink & BrandCyan renkli dinamik gradyan
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            BrandPink.copy(alpha = 0.22f),
            BrandCyan.copy(alpha = 0.12f),
            MaterialTheme.colorScheme.surface
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        if (state.isInitializingPreferences) {
            // Dil ve Tema DataStore'dan yüklenene kadar ortada ProgressBar gösterilir
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator(
                        color = BrandPink,
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        } else {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    com.example.kaprekar.presentation.ui.common.TopGradientAppBar(
                        title = strings.appTitle,
                        state = state,
                        onIntent = onIntent,
                        showBackButton = true
                    )
                }
            ) { innerPadding ->
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding() + 8.dp,
                        bottom = innerPadding.calculateBottomPadding() + 48.dp
                    ),
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
        isInitializingPreferences = false,
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
