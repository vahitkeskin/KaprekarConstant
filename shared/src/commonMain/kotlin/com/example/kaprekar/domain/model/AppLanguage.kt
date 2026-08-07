package com.example.kaprekar.domain.model

enum class AppLanguage(val code: String, val displayName: String, val flagEmoji: String) {
    SYSTEM("system", "System", "🌐"),
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
        fun fromCode(code: String): AppLanguage {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: SYSTEM
        }
    }
}
