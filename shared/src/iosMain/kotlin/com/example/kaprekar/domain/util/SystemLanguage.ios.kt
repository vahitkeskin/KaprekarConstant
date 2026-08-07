package com.example.kaprekar.domain.util

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual fun getSystemLanguageCode(): String {
    return NSLocale.currentLocale.languageCode ?: "en"
}
