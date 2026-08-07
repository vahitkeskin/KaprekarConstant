package com.example.kaprekar.presentation.i18n

import com.example.kaprekar.domain.model.AppLanguage

object StringsProvider {

    fun getStrings(language: AppLanguage): AppStrings {
        return when (language) {
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
        }
    }
}
