package com.vahitkeskin.kaprekar.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val LightColorScheme = lightColorScheme(
    primary = BrandPink,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE4F0),
    onPrimaryContainer = Color(0xFF8B0047),
    secondary = BrandCyan,
    onSecondary = Color(0xFF00363D),
    secondaryContainer = Color(0xFFE0FAFF),
    onSecondaryContainer = Color(0xFF004F59),
    surface = Color(0xFFF8FAFC),
    onSurface = Color(0xFF0F172A),
    background = Color(0xFFF8FAFC),
    onBackground = Color(0xFF0F172A)
)

val DarkColorScheme = darkColorScheme(
    primary = BrandPink,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF5C0030),
    onPrimaryContainer = Color(0xFFFFD9E7),
    secondary = BrandCyan,
    onSecondary = Color(0xFF00363D),
    secondaryContainer = Color(0xFF004F5A),
    onSecondaryContainer = Color(0xFFC4F6FF),
    surface = Color(0xFF0F172A),
    onSurface = Color(0xFFF8FAFC),
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC)
)

@Composable
fun ThemePreview(
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.1f))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Light Mode Section
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = LightColorScheme.background
        ) {
            MaterialTheme(colorScheme = LightColorScheme) {
                Box(modifier = Modifier.padding(16.dp)) {
                    content()
                }
            }
        }

        // Dark Mode Section
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = DarkColorScheme.background
        ) {
            MaterialTheme(colorScheme = DarkColorScheme) {
                Box(modifier = Modifier.padding(16.dp)) {
                    content()
                }
            }
        }
    }
}
