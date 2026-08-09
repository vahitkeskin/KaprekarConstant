package com.vahitkeskin.kaprekar.presentation.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Settings
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
import com.vahitkeskin.kaprekar.domain.model.ThemeMode
import com.vahitkeskin.kaprekar.presentation.KaprekarUiIntent
import com.vahitkeskin.kaprekar.presentation.KaprekarUiState

val BrandPink = Color(0xFFFF2E93)
val BrandCyan = Color(0xFF00F0FF)

@Composable
fun TopGradientAppBar(
    title: String,
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit,
    showBackButton: Boolean = false,
    showRightActions: Boolean = !showBackButton,
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

        // Right Side: Settings button
        if (showRightActions) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                // Settings Button
                Surface(
                    onClick = { onIntent(KaprekarUiIntent.OnNavigateToScreen(com.vahitkeskin.kaprekar.domain.model.MathScreen.SETTINGS)) },
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                    border = BorderStroke(
                        width = 1.dp,
                        color = BrandPink.copy(alpha = 0.45f)
                    ),
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = BrandPink,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TopGradientAppBarPreview() {
    ThemePreview {
        TopGradientAppBar(
            title = "Matematik Formülleri",
            state = KaprekarUiState(),
            onIntent = {},
            showBackButton = true,
            badgeText = "MATH"
        )
    }
}
