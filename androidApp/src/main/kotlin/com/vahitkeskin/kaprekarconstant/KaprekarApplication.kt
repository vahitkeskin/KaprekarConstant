package com.vahitkeskin.kaprekarconstant

import android.app.Application
import com.example.kaprekar.di.initKoin
import org.koin.android.ext.koin.androidContext

class KaprekarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@KaprekarApplication)
        }
    }
}
