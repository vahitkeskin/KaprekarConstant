package com.example.kaprekar.presentation.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.model.ThemeMode
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState

val BrandPink = Color(0xFFFF2E93)
val BrandCyan = Color(0xFF00F0FF)

@Composable
fun TopGradientAppBar(
    title: String,
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    showBackButton: Boolean = false,
    badgeText: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(56.dp)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        // Left Side: Back button or Badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            if (showBackButton) {
                Surface(
                    onClick = { onIntent(KaprekarUiIntent.OnNavigateBack) },
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                    border = BorderStroke(
                        width = 1.dp,
                        color = BrandPink.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = state.strings.backToHome,
                            tint = BrandPink,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            } else if (badgeText != null) {
                Surface(
                    onClick = { onIntent(KaprekarUiIntent.OnToggleInfoDialog(true)) },
                    shape = RoundedCornerShape(12.dp),
                    color = BrandPink.copy(alpha = 0.15f),
                    border = BorderStroke(
                        width = 1.dp,
                        color = BrandPink.copy(alpha = 0.4f)
                    )
                ) {
                    Text(
                        text = badgeText,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = BrandPink
                    )
                }
            }
        }

        // Center Title Badge
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
                text = title,
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

        // Right Side: Language & Theme buttons
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            // Language Button
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

            // Theme Button
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
                            state.strings.systemTheme
                        )
                        ThemeMode.LIGHT -> Triple(
                            Icons.Default.LightMode,
                            Color(0xFFF57C00),
                            state.strings.lightTheme
                        )
                        ThemeMode.DARK -> Triple(
                            Icons.Default.DarkMode,
                            Color(0xFF90CAF9),
                            state.strings.darkTheme
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

@Preview
@Composable
fun TopGradientAppBarPreview() {
    MaterialTheme {
        TopGradientAppBar(
            title = "Matematik Formülleri",
            state = KaprekarUiState(),
            onIntent = {},
            showBackButton = true,
            badgeText = "MATH"
        )
    }
}
