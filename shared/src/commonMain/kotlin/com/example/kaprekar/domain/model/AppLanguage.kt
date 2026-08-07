package com.example.kaprekar.domain.model

import com.example.kaprekar.domain.util.getSystemLanguageCode

enum class AppLanguage(val code: String, val displayName: String, val flagEmoji: String) {
    TR("tr", "Türkçe", "🇹🇷"),
    EN("en", "English", "🇬🇧"),
    JA("ja", "日本語", "🇯🇵"),
    DE("de", "Deutsch", "🇩🇪"),
    RU("ru", "Русский", "🇷🇺"),
    FR("fr", "Français", "🇫🇷"),
    ES("es", "Español", "🇪🇸"),
    HI("hi", "हिन्दी", "🇮🇳"),
    AR("ar", "العربية", "🇸🇦"),
    AZ("az", "Azərbaycan", "🇦🇿"),
    ZH("zh", "中文 (简体)", "🇨🇳"),
    PT("pt", "Português", "🇧🇷"),
    ID("id", "Bahasa Indonesia", "🇮🇩"),
    KO("ko", "한국어", "🇰🇷"),
    IT("it", "Italiano", "🇮🇹"),
    NL("nl", "Nederlands", "🇳🇱"),
    VI("vi", "Tiếng Việt", "🇻🇳"),
    TH("th", "ไทย", "🇹🇭"),
    PL("pl", "Polski", "🇵🇱");

    companion object {
        fun getSystemDefault(): AppLanguage {
            val systemCode = getSystemLanguageCode()
            return entries.firstOrNull { it.code.equals(systemCode, ignoreCase = true) } ?: EN
        }

        fun fromCode(code: String): AppLanguage {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: getSystemDefault()
        }
    }
}
