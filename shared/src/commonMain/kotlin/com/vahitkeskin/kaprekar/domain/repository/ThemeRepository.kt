package com.vahitkeskin.kaprekar.domain.repository

import com.vahitkeskin.kaprekar.domain.model.AppLanguage
import com.vahitkeskin.kaprekar.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    val themeMode: Flow<ThemeMode>
    val appLanguage: Flow<AppLanguage>
    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setAppLanguage(language: AppLanguage)
}
