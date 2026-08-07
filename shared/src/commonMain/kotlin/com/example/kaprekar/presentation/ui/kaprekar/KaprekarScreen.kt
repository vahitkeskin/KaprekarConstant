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
                    // Ekran ortasında tam hizalanmış kibar ve her modda belirgin TopBar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .height(56.dp)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Sol Taraf: Kibar 6174 Logosu Rozeti
                        Surface(
                            onClick = { onIntent(KaprekarUiIntent.OnToggleInfoDialog(true)) },
                            shape = RoundedCornerShape(12.dp),
                            color = BrandPink.copy(alpha = 0.15f),
                            border = BorderStroke(
                                width = 1.dp,
                                color = BrandPink.copy(alpha = 0.4f)
                            ),
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Text(
                                text = "6174",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = BrandPink
                            )
                        }

                        // Tam Ekran Ortası: Yüksek Kontrastlı Kapsül İçinde Belirgin Başlık
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                            border = BorderStroke(
                                width = 1.dp,
                                color = BrandPink.copy(alpha = 0.35f)
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(
                                text = strings.appTitle,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 0.3.sp
                                ),
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }

                        // Sağ Taraf: Kibar Yuvarlak Dil ve Tema Butonları
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            // Yuvarlak Kibar Dil Butonu
                            Surface(
                                onClick = { onIntent(KaprekarUiIntent.OnToggleLanguageDialog(true)) },
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = BrandCyan.copy(alpha = 0.5f)
                                ),
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = state.appLanguage.flagEmoji,
                                        fontSize = 15.sp
                                    )
                                }
                            }

                            // Yuvarlak Kibar Tema Butonu
                            Surface(
                                onClick = { onIntent(KaprekarUiIntent.OnToggleThemeMode) },
                                shape = CircleShape,
                                color = when (state.themeMode) {
                                    ThemeMode.SYSTEM -> MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                                    ThemeMode.LIGHT -> Color(0xFFFFF3E0).copy(alpha = 0.95f)
                                    ThemeMode.DARK -> Color(0xFF263238).copy(alpha = 0.95f)
                                },
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = when (state.themeMode) {
                                        ThemeMode.SYSTEM -> BrandPink.copy(alpha = 0.45f)
                                        ThemeMode.LIGHT -> Color(0xFFFFB74D).copy(alpha = 0.7f)
                                        ThemeMode.DARK -> Color(0xFF90CAF9).copy(alpha = 0.7f)
                                    }
                                ),
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    val (icon, tint, desc) = when (state.themeMode) {
                                        ThemeMode.SYSTEM -> Triple(
                                            Icons.Default.SettingsBrightness,
                                            BrandPink,
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
                                        modifier = Modifier.size(17.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            ) { innerPadding ->
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 48.dp),
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
