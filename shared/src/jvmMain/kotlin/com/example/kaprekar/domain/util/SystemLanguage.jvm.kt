package com.example.kaprekar.domain.util

import java.util.Locale

actual fun getSystemLanguageCode(): String {
    return Locale.getDefault().language ?: "en"
}
