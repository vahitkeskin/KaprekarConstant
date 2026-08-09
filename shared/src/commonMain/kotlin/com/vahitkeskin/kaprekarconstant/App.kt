package com.vahitkeskin.kaprekarconstant

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.vahitkeskin.kaprekar.di.initKoin
import com.vahitkeskin.kaprekar.domain.model.MathScreen
import com.vahitkeskin.kaprekar.domain.model.ThemeMode
import com.vahitkeskin.kaprekar.presentation.KaprekarUiIntent
import com.vahitkeskin.kaprekar.presentation.KaprekarViewModel
import com.vahitkeskin.kaprekar.presentation.ui.chaosgame.ChaosGameScreen
import com.vahitkeskin.kaprekar.presentation.ui.collatz.CollatzScreen
import com.vahitkeskin.kaprekar.presentation.ui.common.BackHandler
import com.vahitkeskin.kaprekar.presentation.ui.euclid.EuclidGcdScreen
import com.vahitkeskin.kaprekar.presentation.ui.euler.EulerScreen
import com.vahitkeskin.kaprekar.presentation.ui.fibonacci.FibonacciScreen
import com.vahitkeskin.kaprekar.presentation.ui.fourier.FourierScreen
import com.vahitkeskin.kaprekar.presentation.ui.fractal.FractalScreen
import com.vahitkeskin.kaprekar.presentation.ui.goldenratio.GoldenRatioScreen
import com.vahitkeskin.kaprekar.presentation.ui.home.HomeScreen
import com.vahitkeskin.kaprekar.presentation.ui.kaprekar.LanguageSelectionDialog
import com.vahitkeskin.kaprekar.presentation.ui.kaprekar.KaprekarScreen
import com.vahitkeskin.kaprekar.presentation.ui.modular.ModularScreen
import com.vahitkeskin.kaprekar.presentation.ui.nimgame.NimGameScreen
import com.vahitkeskin.kaprekar.presentation.ui.pascal.PascalScreen
import com.vahitkeskin.kaprekar.presentation.ui.phyllotaxis.PhyllotaxisScreen
import com.vahitkeskin.kaprekar.presentation.ui.pi.PiScreen
import com.vahitkeskin.kaprekar.presentation.ui.prime.PrimeScreen
import com.vahitkeskin.kaprekar.presentation.ui.quadratic.QuadraticScreen
import com.vahitkeskin.kaprekar.presentation.ui.statistics.StatisticsScreen
import com.vahitkeskin.kaprekar.presentation.ui.supernumber.SuperNumberScreen
import com.vahitkeskin.kaprekar.presentation.ui.transformation.TransformationScreen
import com.vahitkeskin.kaprekar.presentation.ui.trigonometry.TrigonometryScreen
import com.vahitkeskin.kaprekar.presentation.ui.settings.SettingsScreen
import com.vahitkeskin.kaprekar.presentation.ui.logarithm.LogarithmScreen
import com.vahitkeskin.kaprekar.presentation.ui.arf.ArfInvariantScreen
import com.vahitkeskin.kaprekar.presentation.ui.thales.ThalesScreen
import com.vahitkeskin.kaprekar.presentation.ui.kepler.KeplerScreen
import com.vahitkeskin.kaprekar.presentation.ui.brachistochrone.BrachistochroneScreen
import com.vahitkeskin.kaprekar.presentation.ui.cantor.CantorScreen
import com.vahitkeskin.kaprekar.presentation.ui.eratosthenes.EratosthenesScreen
import com.vahitkeskin.kaprekar.presentation.ui.cubic.CubicScreen
import com.vahitkeskin.kaprekar.presentation.ui.sphericaltrig.SphericalTrigScreen
import com.vahitkeskin.kaprekar.presentation.ui.godel.GodelScreen
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

import androidx.compose.runtime.saveable.rememberSaveableStateHolder

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
    val saveableStateHolder = rememberSaveableStateHolder()

    // System Back Gesture / Button Handler
    BackHandler(enabled = state.canNavigateBack) {
        viewModel.onIntent(KaprekarUiIntent.OnNavigateBack)
    }

    val useDarkTheme = when (state.themeMode) {
        ThemeMode.SYSTEM -> isSystemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme) {
        if (state.showLanguageDialog) {
            LanguageSelectionDialog(
                state = state,
                onSelect = { viewModel.onIntent(KaprekarUiIntent.OnSelectLanguage(it)) },
                onDismiss = { viewModel.onIntent(KaprekarUiIntent.OnToggleLanguageDialog(false)) }
            )
        }

        val fadeSpec = tween<Float>(durationMillis = 300)

        AnimatedContent(
            targetState = state.currentScreen,
            transitionSpec = {
                fadeIn(animationSpec = fadeSpec) togetherWith fadeOut(animationSpec = fadeSpec)
            }
        ) { targetScreen ->
            saveableStateHolder.SaveableStateProvider(targetScreen) {
                when (targetScreen) {
                    MathScreen.HOME -> HomeScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.KAPREKAR -> KaprekarScreen(viewModel = viewModel)
                    MathScreen.FIBONACCI -> FibonacciScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.SUPER_NUMBER -> SuperNumberScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.GOLDEN_RATIO -> GoldenRatioScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.COLLATZ -> CollatzScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.PRIME -> PrimeScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.PASCAL -> PascalScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.PI -> PiScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.EULER -> EulerScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.EUCLID_GCD -> EuclidGcdScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.TRIGONOMETRY -> TrigonometryScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.QUADRATIC -> QuadraticScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.MODULAR -> ModularScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.STATISTICS -> StatisticsScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.FRACTAL -> FractalScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.PHYLLOTAXIS -> PhyllotaxisScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.TRANSFORMATION -> TransformationScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.FOURIER -> FourierScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.CHAOS_GAME -> ChaosGameScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.NIM_GAME -> NimGameScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.LOGARITHM -> LogarithmScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.ARF_INVARIANT -> ArfInvariantScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.THALES -> ThalesScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.KEPLER_LAWS -> KeplerScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.BRACHISTOCHRONE -> BrachistochroneScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.CANTOR_SET -> CantorScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.ERATOSTHENES -> EratosthenesScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.CUBIC_EQUATION -> CubicScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.SPHERICAL_TRIG -> SphericalTrigScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.GODEL_NUMBERING -> GodelScreen(state = state, onIntent = viewModel::onIntent)
                    MathScreen.SETTINGS -> SettingsScreen(state = state, onIntent = viewModel::onIntent)
                }
            }
        }
    }
}