package com.example.kaprekar.presentation.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.model.AppLanguage
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

    // Animasyonlu Yavaş Ortalamam: Yeni her adım açıldığında aktif adımı ekran ortasına kaydırır
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
                        // Yuvarlak Dil Seçim Butonu (Düz Tasarım - Gölge / Elevation Yok)
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

                        // Sağ Üstte Yuvarlak Tema Değiştirme Butonu (Düz Tasarım - Gölge / Elevation Yok)
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
                // Bilgilendirme Kartı (Header Banner) - Gölgeler Kaldırıldı
                item {
                    HeaderBannerCard(
                        state = state,
                        onInfoClick = { onIntent(KaprekarUiIntent.OnToggleInfoDialog(true)) }
                    )
                }

                // Sayı Giriş Bölümü Kartı - Gölgeler Kaldırıldı
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

                // Adım Adım Hesaplama Başlığı
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

@Composable
fun HeaderBannerCard(
    state: KaprekarUiState,
    onInfoClick: () -> Unit
) {
    val strings = state.strings
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = strings.magicTitle,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                    IconButton(onClick = onInfoClick) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = strings.infoTitle,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = strings.magicDescription,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
                        lineHeight = 16.sp
                    )
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                modifier = Modifier.size(54.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

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

@Composable
fun DigitBadge(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.outline
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun SuccessBannerCard(state: KaprekarUiState, totalSteps: Int) {
    val strings = state.strings
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = strings.successTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = strings.successDescriptionFormat.format(totalSteps),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun KaprekarInfoDialog(
    state: KaprekarUiState,
    onDismiss: () -> Unit
) {
    val strings = state.strings
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(strings.infoTitle)
            }
        },
        text = {
            Text(
                text = strings.infoDescription,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
fun LanguageSelectionDialog(
    state: KaprekarUiState,
    onSelect: (AppLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    val strings = state.strings
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = strings.languageSelectTitle,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(max = 360.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(AppLanguage.entries) { lang ->
                    val isSelected = state.appLanguage == lang
                    Surface(
                        onClick = { onSelect(lang) },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = lang.flagEmoji, fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = lang.displayName,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(strings.resetButton)
            }
        }
    )
}
