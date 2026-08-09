package com.example.kaprekar.presentation.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaprekar.domain.model.AppLanguage
import com.example.kaprekar.domain.model.ThemeMode
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

private data class SettingsStrings(
    val title: String,
    val themeSection: String,
    val systemTheme: String,
    val lightTheme: String,
    val darkTheme: String,
    val languageSection: String,
    val languageSubtitle: String
)

private fun getSettingsStrings(language: AppLanguage): SettingsStrings {
    return when (language) {
        AppLanguage.TR -> SettingsStrings(
            title = "Ayarlar",
            themeSection = "Görünüm Teması",
            systemTheme = "Sistem Varsayılanı",
            lightTheme = "Açık Tema",
            darkTheme = "Karanlık Tema",
            languageSection = "Uygulama Dili",
            languageSubtitle = "Uygulama genelinde kullanılacak dil"
        )
        AppLanguage.DE -> SettingsStrings(
            title = "Einstellungen",
            themeSection = "Thema",
            systemTheme = "Systemstandard",
            lightTheme = "Helles Thema",
            darkTheme = "Dunkles Thema",
            languageSection = "App-Sprache",
            languageSubtitle = "Wählen Sie Ihre bevorzugte Sprache"
        )
        AppLanguage.FR -> SettingsStrings(
            title = "Paramètres",
            themeSection = "Thème de l'application",
            systemTheme = "Système",
            lightTheme = "Thème clair",
            darkTheme = "Thème sombre",
            languageSection = "Langue",
            languageSubtitle = "Langue générale de l'application"
        )
        AppLanguage.ES -> SettingsStrings(
            title = "Ajustes",
            themeSection = "Tema",
            systemTheme = "Predeterminado del sistema",
            lightTheme = "Tema claro",
            darkTheme = "Tema oscuro",
            languageSection = "Idioma",
            languageSubtitle = "Idioma general de la aplicación"
        )
        AppLanguage.RU -> SettingsStrings(
            title = "Настройки",
            themeSection = "Тема оформления",
            systemTheme = "Системная тема",
            lightTheme = "Светлая тема",
            darkTheme = "Темная тема",
            languageSection = "Язык приложения",
            languageSubtitle = "Выберите язык интерфейса"
        )
        else -> SettingsStrings(
            title = "Settings",
            themeSection = "Appearance Theme",
            systemTheme = "System Default",
            lightTheme = "Light Theme",
            darkTheme = "Dark Theme",
            languageSection = "App Language",
            languageSubtitle = "Language used throughout the app"
        )
    }
}

@Composable
fun SettingsScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit
) {
    val settingsStrings = getSettingsStrings(state.appLanguage)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            BrandCyan.copy(alpha = 0.15f),
            BrandPink.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.surface
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopGradientAppBar(
                    title = settingsStrings.title,
                    state = state,
                    onIntent = onIntent,
                    showBackButton = true
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Theme Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = settingsStrings.themeSection,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        ThemeOptionRow(
                            label = settingsStrings.systemTheme,
                            isSelected = state.themeMode == ThemeMode.SYSTEM,
                            icon = Icons.Default.SettingsBrightness,
                            onClick = { onIntent(KaprekarUiIntent.OnSelectThemeMode(ThemeMode.SYSTEM)) }
                        )

                        ThemeOptionRow(
                            label = settingsStrings.lightTheme,
                            isSelected = state.themeMode == ThemeMode.LIGHT,
                            icon = Icons.Default.LightMode,
                            onClick = { onIntent(KaprekarUiIntent.OnSelectThemeMode(ThemeMode.LIGHT)) }
                        )

                        ThemeOptionRow(
                            label = settingsStrings.darkTheme,
                            isSelected = state.themeMode == ThemeMode.DARK,
                            icon = Icons.Default.DarkMode,
                            onClick = { onIntent(KaprekarUiIntent.OnSelectThemeMode(ThemeMode.DARK)) }
                        )
                    }
                }

                // Language Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = settingsStrings.languageSection,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Surface(
                            onClick = { onIntent(KaprekarUiIntent.OnToggleLanguageDialog(true)) },
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Language,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Column {
                                        Text(
                                            text = state.appLanguage.displayName + " " + state.appLanguage.flagEmoji,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 15.sp
                                        )
                                        Text(
                                            text = settingsStrings.languageSubtitle,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ThemeOptionRow(
    label: String,
    isSelected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        border = if (isSelected) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        } else {
            BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
