package com.vahitkeskin.kaprekarconstant

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.di.initKoin
import com.example.kaprekar.domain.model.ThemeMode
import com.example.kaprekar.presentation.KaprekarViewModel
import com.example.kaprekar.presentation.ui.kaprekar.KaprekarScreen
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

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

    val colorScheme = if (useDarkTheme) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        KaprekarScreen(viewModel = viewModel)
    }
}