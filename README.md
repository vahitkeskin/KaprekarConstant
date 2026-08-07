# 🔮 Kaprekar's Constant (6174) — Multiplatform App 🚀

[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.10-7F52FF.svg?logo=kotlin)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.11.1-4285F4.svg?logo=jetpackcompose)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Platforms](https://img.shields.io/badge/Platforms-Android%20%7C%20iOS%20%7C%20Desktop-green.svg)](#-platforms-support)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20%7C%20MVI--MVVM-orange.svg)](#-architecture--tech-stack)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**Kaprekar's Constant (6174)** is a modern, production-ready, highly interactive Kotlin Multiplatform (KMP) application built with **Compose Multiplatform**, **Material 3**, **Koin DI**, and **DataStore Preferences**. 

It demonstrates the fascinating mathematical phenomenon of **Kaprekar's Routine** across **Android**, **iOS**, and **Desktop (JVM)** with real-time step calculations, slow step-by-step reveal animations, auto-scroll step centering, 19 international languages, and persistent theme settings! 🌟

---

## 🌟 Key Features (Öne Çıkan Özellikler)

- 🧮 **Pure Kotlin Domain Algorithm**: Calculates Kaprekar's Routine step-by-step for any valid 4-digit number (with at least 2 distinct digits). Automatically handles intermediate 4-digit leading zero padding ($D - A = Result$).
- 🌐 **19 International Languages (19 Dil Desteği)**: Full multi-language support with instant switching (`tr`, `en`, `ja`, `de`, `ru`, `fr`, `es`, `hi`, `ar`, `az`, `zh`, `pt`, `id`, `ko`, `it`, `nl`, `vi`, `th`, `pl`). Automatically detects system locale and defaults to **English (`en`)** if unlisted!
- 🎨 **Dynamic Theme Modes (Sistem, Açık, Kapalı)**: Smooth switching between System Default (Default), Light, and Dark themes with custom Material 3 color palettes and circular action button.
- 💾 **KMP DataStore Preferences Persistence**: Preferences (`theme_mode`, `app_language`) are saved cross-platform using `androidx.datastore` and restored seamlessly on app startup.
- 🎬 **Animated Slow Reveal & Auto-Centering**: Steps are revealed smoothly with a 1000ms delay per step. The active step automatically scrolls to center focus on screen (`animateScrollToItem`).
- 🔘 **Circular TopBar Action Buttons**: Custom circular flag button for language selection and circular theme toggle button in top right.
- 📱 **Multiplatform Native Performance**: Runs natively on Android, iOS (SwiftUI host), and Desktop JVM (Mac, Windows, Linux).

---

## 📐 Architecture & Tech Stack (Mimari ve Teknolojiler)

The application follows strict **Clean Architecture** principles and **Unidirectional Data Flow (MVI/MVVM)**:

```
           ┌─────────────────────────────────────────┐
           │              UI Layer                   │
           │  (KaprekarScreen & Compose Multiplatform)│
           └────────────────────┬────────────────────┘
                                │ Intent / Action
                                ▼
           ┌─────────────────────────────────────────┐
           │           Presentation Layer            │
           │   (KaprekarViewModel & StateFlow UIState)│
           └────────────────────┬────────────────────┘
                                │ Executes
                                ▼
           ┌─────────────────────────────────────────┐
           │              Domain Layer               │
           │ (CalculateKaprekarUseCase & KaprekarStep)│
           └────────────────────┬────────────────────┘
                                │ Reads / Writes
                                ▼
           ┌─────────────────────────────────────────┐
           │               Data Layer                │
           │(ThemeRepositoryImpl & DataStore Prefs)  │
           └─────────────────────────────────────────┘
```

### 🛠️ Core Technologies
- **UI Framework**: Compose Multiplatform (Material 3)
- **State Management**: Kotlin Coroutines & `StateFlow`
- **Dependency Injection**: Koin 4.0.2 (KMP ready with platform modules)
- **Data Persistence**: AndroidX DataStore Preferences Core 1.1.3
- **Localisation**: Modular Kotlin String objects (`AppStrings.kt`, `TrStrings.kt`, `EnStrings.kt`, etc.)
- **Build System**: Gradle 9.x with Version Catalogs (`libs.versions.toml`)

---

## 🧮 What is Kaprekar's Constant? (6174 Algoritması)

Discovered in 1949 by Indian mathematician **D. R. Kaprekar**, the number **6174** is known as Kaprekar's Constant.

### 📜 Algorithm Rules:
1. Choose any 4-digit number that has at least two distinct digits (e.g. `6825`, `3524`, `1000`).
2. Sort the digits in descending order ($D$) and ascending order ($A$).
3. Subtract ascending from descending: $Result = D - A$.
4. Repeat the process with the result (padded to 4 digits if needed, e.g. `0999`).
5. You will **ALWAYS** reach **6174** in **7 iterations or fewer**!

#### 💡 Example (Input: `3524`):
- **Step 1**: `5432 - 2345 = 3087`
- **Step 2**: `8730 - 0378 = 8352`
- **Step 3**: `8532 - 2358 = 6174` 🎯 *(Reached in 3 steps!)*

---

## 🌐 Supported Languages (Desteklenen Diller)

| Code | Flag | Language | Kotlin Source File |
| :--- | :--- | :--- | :--- |
| `system` | 🌐 | System Default | Automatic System Locale |
| `tr` | 🇹🇷 | Türkçe | [TrStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/TrStrings.kt) |
| `en` | 🇬🇧 | English | [EnStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/EnStrings.kt) |
| `ja` | 🇯🇵 | 日本語 | [JaStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/JaStrings.kt) |
| `de` | 🇩🇪 | Deutsch | [DeStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/DeStrings.kt) |
| `ru` | 🇷🇺 | Русский | [RuStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/RuStrings.kt) |
| `fr` | 🇫🇷 | Français | [FrStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/FrStrings.kt) |
| `es` | 🇪🇸 | Español | [EsStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/EsStrings.kt) |
| `hi` | 🇮🇳 | हिन्दी | [HiStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/HiStrings.kt) |
| `ar` | 🇸🇦 | العربية | [ArStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/ArStrings.kt) |
| `az` | 🇦🇿 | Azərbaycan | [AzStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/AzStrings.kt) |
| `zh` | 🇨🇳 | 中文 (简体) | [ZhStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/ZhStrings.kt) |
| `pt` | 🇧🇷 | Português | [PtStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/PtStrings.kt) |
| `id` | 🇮🇩 | Bahasa Indonesia | [IdStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/IdStrings.kt) |
| `ko` | 🇰🇷 | 한국어 | [KoStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/KoStrings.kt) |
| `it` | 🇮🇹 | Italiano | [ItStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/ItStrings.kt) |
| `nl` | 🇳🇱 | Nederlands | [NlStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/NlStrings.kt) |
| `vi` | 🇻🇳 | Tiếng Việt | [ViStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/ViStrings.kt) |
| `th` | 🇹🇭 | ไทย | [ThStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/ThStrings.kt) |
| `pl` | 🇵🇱 | Polski | [PlStrings.kt](shared/src/commonMain/kotlin/com/example/kaprekar/presentation/i18n/PlStrings.kt) |

---

## 📁 Repository Directory Structure

```text
KaprekarConstant/
├── androidApp/                       # Android Target Module
│   └── src/main/kotlin/
│       └── com/vahitkeskin/kaprekarconstant/
│           ├── KaprekarApplication.kt # Koin Context & Application Class
│           └── MainActivity.kt       # Activity Entry Point
├── desktopApp/                       # Desktop JVM Target Module
│   └── src/main/kotlin/
│       └── com/vahitkeskin/kaprekarconstant/main.kt # Desktop Entry Point
├── iosApp/                           # iOS Target Module (SwiftUI)
│   └── iosApp/iOSApp.swift           # iOS Entry Point
└── shared/                           # KMP Shared Logic & UI
    └── src/
        ├── commonMain/kotlin/com/example/kaprekar/
        │   ├── data/
        │   │   ├── datastore/        # PreferenceDataStore Factory
        │   │   └── repository/       # ThemeRepositoryImpl (DataStore)
        │   ├── di/                   # Koin DI Modules & initKoin()
        │   ├── domain/
        │   │   ├── model/            # KaprekarStep, ThemeMode, AppLanguage
        │   │   ├── repository/       # ThemeRepository Interface
        │   │   ├── usecase/          # CalculateKaprekarUseCase Pure Domain Logic
        │   │   └── util/             # SystemLanguage expect fun
        │   └── presentation/
        │       ├── KaprekarContract.kt # MVI State & Actions
        │       ├── KaprekarViewModel.kt # ViewModel with StateFlow
        │       ├── i18n/              # AppStrings & 19 Language Files
        │       └── ui/
        │           └── KaprekarScreen.kt # Compose Multiplatform UI
        ├── androidMain/               # Android Platform Actual Implementations
        ├── iosMain/                   # iOS Platform Actual Implementations
        └── jvmMain/                   # Desktop Platform Actual Implementations
```

---

## 🚀 Getting Started & How to Run (Nasıl Çalıştırılır?)

### 📋 Prerequisites
- **JDK 17+**
- **Android SDK** (for Android build)
- **Xcode** (for iOS build on macOS)

### 🛠️ Execution Commands

#### 🤖 Run Android App
```bash
./gradlew :androidApp:assembleDebug
```

#### 🖥️ Run Desktop App (JVM)
```bash
./gradlew :desktopApp:run
```

#### 🧪 Run Unit Tests
```bash
./gradlew :shared:jvmTest
```

---

## 🧪 Unit Testing (Birim Testleri)

Pure domain logic is covered with comprehensive KMP unit tests in [CalculateKaprekarUseCaseTest.kt](shared/src/commonTest/kotlin/com/example/kaprekar/domain/usecase/CalculateKaprekarUseCaseTest.kt):

- ✅ **Valid Inputs**: Verifies `6825`, `3524`, `1000`, `0123`.
- ❌ **Uniform Rejection**: Rejects `1111`, `0000`, `9999` with appropriate validation errors.
- 🔢 **Zero-Padding**: Validates intermediate leading zero padding (e.g. `1000` -> `0001` -> `0999`).
- 🎯 **Kaprekar Step Accuracy**: Guarantees `3524` reaches `6174` in exactly 3 steps.

---

## 📜 License

```text
Copyright (c) 2026 Vahit Keskin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

<p align="center">
Made with ❤️ & ☕ using <b>Kotlin Multiplatform</b> & <b>Jetpack Compose</b>.
</p>