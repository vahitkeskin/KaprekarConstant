package com.example.kaprekar.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.kaprekar.domain.model.AppLanguage
import com.example.kaprekar.domain.model.ThemeMode
import com.example.kaprekar.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : ThemeRepository {

    private val themeKey = stringPreferencesKey("theme_mode")
    private val languageKey = stringPreferencesKey("app_language")

    override val themeMode: Flow<ThemeMode> = dataStore.data.map { preferences ->
        val modeName = preferences[themeKey] ?: ThemeMode.SYSTEM.name
        try {
            ThemeMode.valueOf(modeName)
        } catch (_: Exception) {
            ThemeMode.SYSTEM
        }
    }

    override val appLanguage: Flow<AppLanguage> = dataStore.data.map { preferences ->
        val langCode = preferences[languageKey] ?: AppLanguage.SYSTEM.code
        AppLanguage.fromCode(langCode)
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[themeKey] = mode.name
        }
    }

    override suspend fun setAppLanguage(language: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[languageKey] = language.code
        }
    }
}
