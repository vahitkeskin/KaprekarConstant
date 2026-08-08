# 🔮 Kaprekar's Constant (6174) & Math Lab — Multiplatform App 🚀

[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.10-7F52FF.svg?logo=kotlin)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.11.1-4285F4.svg?logo=jetpackcompose)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Platforms](https://img.shields.io/badge/Platforms-Android%20%7C%20iOS%20%7C%20Desktop-green.svg)](#-platforms-support)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20%7C%20MVI--MVVM-orange.svg)](#-architecture--tech-stack)
[![Solvers](https://img.shields.io/badge/Math_Solvers-30_Interactive_Modules-magenta.svg)](#-30-interactive-math-solvers--visualizers)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**Kaprekar's Constant (6174) & Math Lab** is a modern, production-ready, highly interactive Kotlin Multiplatform (KMP) application built with **Compose Multiplatform**, **Material 3**, **Koin DI**, and **DataStore Preferences**.

It demonstrates the fascinating mathematical phenomenon of **Kaprekar's Routine** alongside **30 interactive mathematical solvers and visualizers** across **Android**, **iOS**, and **Desktop (JVM)** with real-time step calculations, slow reveal animations, auto-scroll step centering, home search filtering, 19 international languages, custom 3D Pi branding, and persistent settings! 🌟

---

## 🌟 Key Features (Öne Çıkan Özellikler)

- 🧮 **30 Interactive Math Solvers & Visualizers**: Includes Kaprekar Routine, Fibonacci, Collatz, Arf Invariant, Thales, Kepler Laws, Brachistochrone, Cantor Set, Eratosthenes, Cardano Cubic, Spherical Trig, Gödel Numbering, and more!
- 🔍 **Home Search & Live Filtering**: Instant search filtering across all 30 math modules on the home dashboard.
- 🎨 **Minimalist Solid 3D Pi App Icon**: Uniform brand icon across Android (`mipmap-*`), iOS (`AppIcon.appiconset`), and Desktop (`desktopApp`).
- 🌐 **19 International Languages (19 Dil Desteği)**: Full multi-language support with instant switching (`tr`, `en`, `ja`, `de`, `ru`, `fr`, `es`, `hi`, `ar`, `az`, `zh`, `pt`, `id`, `ko`, `it`, `nl`, `vi`, `th`, `pl`). Automatically detects system locale and defaults to **English (`en`)** if unlisted!
- 🎨 **Dynamic Theme Modes (Sistem, Açık, Kapalı)**: Smooth switching between System Default (Default), Light, and Dark themes with custom Material 3 color palettes and circular action button.
- 💾 **KMP DataStore Preferences Persistence**: Preferences (`theme_mode`, `app_language`) are saved cross-platform using `androidx.datastore` and restored seamlessly on app startup.
- 🎬 **Smooth Fade Screen Transitions**: Clean fade transitions (`fadeIn` + `fadeOut`) without screen movement clutter.
- 📱 **Multiplatform Native Edge-to-Edge**: 100% transparent navigation bar and edge-to-edge support across Android, iOS (SwiftUI host), and Desktop JVM.

---

## 🔢 30 Interactive Math Solvers & Visualizers (30 Matematiksel Hesaplayıcı)

| # | Solver / Feature | Description | Reference / Inspired By |
| :-: | :--- | :--- | :--- |
| **1** | 🔮 **Kaprekar Routine (6174)** | Interactive step-by-step 4-digit Kaprekar constant calculator with slow reveal animation. | D. R. Kaprekar (1949) |
| **2** | 🌀 **Fibonacci & Golden Ratio** | Fibonacci sequence generation & golden spiral ratio visualization. | Leonardo Fibonacci |
| **3** | 🌟 **Super Number (Armstrong)** | Narcissistic number verifier & step-by-step power-sum calculations. | Number Theory |
| **4** | 📐 **Golden Ratio ($\phi$)** | Golden ratio calculation ($\phi \approx 1.6180339887...$) with geometric proportions. | Euclid & Phidias |
| **5** | 📈 **Collatz Conjecture ($3n+1$)** | Hailstone sequence path graph & step count visualization. | Lothar Collatz |
| **6** | 🔢 **Prime Numbers (Sieve)** | Sieve of Eratosthenes prime generator, primality test & factorization. | Eratosthenes |
| **7** | 🔺 **Pascal's Triangle** | Dynamic Pascal triangle generator with combination values. | Blaise Pascal |
| **8** | 🥧 **Pi ($\pi$) Calculator** | Multi-algorithm Pi estimation (Leibniz series & Nilakantha series). | Gottfried Leibniz |
| **9** | ℯ **Euler's Constant ($e$)** | Calculation of $e \approx 2.71828...$ via infinite sum Taylor expansion. | Leonhard Euler |
| **10** | 🧮 **Euclid GCD & LCM** | Greatest Common Divisor & Least Common Multiple step-by-step Euclidean algorithm. | Euclid |
| **11** | 📐 **Trigonometry & Unit Circle** | Interactive unit circle, Sine, Cosine, Tangent values and radian/degree conversion. | Hipparchus & Ptolemy |
| **12** | 📉 **Quadratic Equation Solver** | $ax^2 + bx + c = 0$ solver with real and complex discriminant ($\Delta$) roots. | Al-Khwarizmi |
| **13** | 🔢 **Modular Arithmetic** | Congruence relations, modulo addition, multiplication and power steps. | Carl Friedrich Gauss |
| **14** | 📊 **Statistics & Data Analysis** | Mean, median, mode, variance, standard deviation & min/max summary. | Carl Friedrich Gauss |
| **15** | ❄️ **Fractal Explorer** | Interactive Mandelbrot and Julia set fractal rendering. | Benoit Mandelbrot |
| **16** | 🌻 **Phyllotaxis Simulation** | Golden ratio sunflower seed spiral growth patterns. | Phyllotaxis Geometry |
| **17** | 🔄 **Transformation Geometry** | Translation, rotation, scaling & reflection coordinate calculations. | Linear Algebra |
| **18** | 🌊 **Fourier Transform** | Waveform decomposition & frequency spectrum visualizer. | Joseph Fourier |
| **19** | 🎲 **Chaos Game** | Sierpinski triangle fractal generation using random vertex play. | Wacław Sierpiński |
| **20** | 🎯 **Nim Game** | Interactive Game Theory strategy game vs AI opponent. | Charles L. Bouton |
| **21** | 📐 **Logarithm & Scales** | Logarithm $\log_b(x)$, $\ln(x)$, Richter scale, Decibel sound & pH calculation. | John Napier |
| **22** | 🇹🇷 **Arf Invariant** | Binary quadratic form invariant $Arf(Q) \in \mathbb{Z}_2$ & knot theory invariant. | Cahit Arf (10 TL) |
| **23** | 📐 **Thales Intercept Theorem** | Pyramid height calculation ($H = h \cdot S/s$) & triangle proportionality. | Thales of Miletus |
| **24** | 🪐 **Kepler's Planetary Laws** | Elliptical orbit simulator ($r(\theta)$, $T^2=a^3$) & perihelion/aphelion speed ratio. | Johannes Kepler |
| **25** | 🏎️ **Brachistochrone Curve** | Cycloid fastest descent curve race vs straight line ($T_{\text{cycloid}} = \pi \sqrt{h/g}$). | Bernoulli Brothers |
| **26** | ♾️ **Cantor Set & Infinities** | Cantor set middle-third fractal step removal & Hausdorff dimension ($D \approx 0.6309$). | Georg Cantor |
| **27** | 🌍 **Eratosthenes Earth Radius** | Alexandria-Syene shadow angle ($\theta = 7.2^\circ$) Earth circumference calculation. | Eratosthenes |
| **28** | 🧩 **Cardano-Tartaglia Cubic** | $ax^3+bx^2+cx+d=0$ depressed cubic substitution ($t^3+pt+q=0$) & 3 roots. | Tartaglia & Cardano |
| **29** | ✈️ **Spherical Trigonometry** | Spherical law of cosines & Haversine great-circle flight distance calculator. | Al-Battani |
| **30** | 🔒 **Gödel Numbering** | Prime exponent encoding ($G = 2^{a_1} \cdot 3^{a_2} \cdot 5^{a_3} \cdots$) of math formulas. | Kurt Gödel |

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
           │   (Domain UseCases & Math Models)       │
           └────────────────────┬────────────────────┘
                                │ Reads / Writes
                                ▼
           ┌─────────────────────────────────────────┐
           │               Data Layer                │
           │(ThemeRepositoryImpl & DataStore Prefs)  │
           └─────────────────────────────────────────┘
```

### 🛠️ Core Technologies
- **UI Framework**: Compose Multiplatform 1.11.1 (Material 3)
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
│   └── src/main/
│       ├── res/                      # Android Launcher Icons (mipmap-*)
│       └── kotlin/com/vahitkeskin/kaprekarconstant/
│           ├── KaprekarApplication.kt # Koin Context & Application Class
│           └── MainActivity.kt       # Activity Entry Point
├── desktopApp/                       # Desktop JVM Target Module
│   └── src/main/
│       ├── resources/                # Desktop Icon (icon.png)
│       └── kotlin/com/vahitkeskin/kaprekarconstant/main.kt # Desktop Entry Point
├── iosApp/                           # iOS Target Module (SwiftUI)
│   └── iosApp/
│       ├── Assets.xcassets/AppIcon.appiconset/ # iOS App Icon Assets
│       └── iOSApp.swift              # iOS Entry Point
└── shared/                           # KMP Shared Logic & UI
    └── src/
        ├── commonMain/kotlin/com/example/kaprekar/
        │   ├── data/
        │   │   ├── datastore/        # PreferenceDataStore Factory
        │   │   └── repository/       # ThemeRepositoryImpl (DataStore)
        │   ├── di/                   # Koin DI Modules & initKoin()
        │   ├── domain/
        │   │   ├── model/            # KaprekarStep, ThemeMode, AppLanguage, MathScreen
        │   │   ├── repository/       # ThemeRepository Interface
        │   │   ├── usecase/          # 20 Math Domain UseCases
        │   │   └── util/             # SystemLanguage expect fun
        │   └── presentation/
        │       ├── KaprekarContract.kt # MVI State & Actions
        │       ├── KaprekarViewModel.kt # ViewModel with StateFlow
        │       ├── i18n/              # AppStrings & 19 Language Files
        │       └── ui/               # 20 Interactive Screen UIs & Navigation
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