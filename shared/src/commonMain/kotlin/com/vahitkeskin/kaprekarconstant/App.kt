package com.vahitkeskin.kaprekarconstant

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.di.initKoin
import com.example.kaprekar.domain.model.ThemeMode
import com.example.kaprekar.presentation.KaprekarViewModel
import com.example.kaprekar.presentation.ui.kaprekar.KaprekarScreen
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

val BrandPink = Color(0xFFFF2E93)
val BrandCyan = Color(0xFF00F0FF)

private val LightColorScheme = lightColorScheme(
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

private val DarkColorScheme = darkColorScheme(
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
@Preview
fun App() {
    initKoin()
    KoinContext {
        KaprekarAppContent()
    }
}

@Composable
fun KaprekarAppContent(
    viewModel: KaprekarViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val isSystemDark = isSystemInDarkTheme()

    val useDarkTheme = when (state.themeMode) {
        ThemeMode.SYSTEM -> isSystemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme) {
        KaprekarScreen(viewModel = viewModel)
    }
}