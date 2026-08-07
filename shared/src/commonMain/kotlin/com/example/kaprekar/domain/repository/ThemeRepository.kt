package com.example.kaprekar.domain.repository

import com.example.kaprekar.domain.model.AppLanguage
import com.example.kaprekar.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    val themeMode: Flow<ThemeMode>
    val appLanguage: Flow<AppLanguage>
    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setAppLanguage(language: AppLanguage)
}
