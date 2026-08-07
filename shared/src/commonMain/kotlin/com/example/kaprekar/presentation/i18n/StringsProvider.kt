package com.example.kaprekar.presentation.i18n

import com.example.kaprekar.domain.model.AppLanguage
import com.example.kaprekar.domain.util.getSystemLanguageCode

object StringsProvider {

    fun getStrings(language: AppLanguage): AppStrings {
        val targetLanguage = if (language == AppLanguage.SYSTEM) {
            val systemCode = getSystemLanguageCode()
            AppLanguage.entries.firstOrNull { it != AppLanguage.SYSTEM && it.code.equals(systemCode, ignoreCase = true) } ?: AppLanguage.EN
        } else {
            language
        }

        return when (targetLanguage) {
            AppLanguage.TR -> TrStrings
            AppLanguage.EN -> EnStrings
            AppLanguage.JA -> JaStrings
            AppLanguage.DE -> DeStrings
            AppLanguage.RU -> RuStrings
            AppLanguage.FR -> FrStrings
            AppLanguage.ES -> EsStrings
            AppLanguage.HI -> HiStrings
            AppLanguage.AR -> ArStrings
            AppLanguage.AZ -> AzStrings
            AppLanguage.ZH -> ZhStrings
            AppLanguage.PT -> PtStrings
            AppLanguage.ID -> IdStrings
            AppLanguage.KO -> KoStrings
            AppLanguage.IT -> ItStrings
            AppLanguage.NL -> NlStrings
            AppLanguage.VI -> ViStrings
            AppLanguage.TH -> ThStrings
            AppLanguage.PL -> PlStrings
            AppLanguage.SYSTEM -> EnStrings
        }
    }
}
