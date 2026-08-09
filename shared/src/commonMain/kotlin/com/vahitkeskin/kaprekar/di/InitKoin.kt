package com.vahitkeskin.kaprekar.di

import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            appDeclaration()
            modules(appModule)
        }
    } else {
        GlobalContext.get()
    }
