package com.vahitkeskin.kaprekarconstant

import android.app.Application
import com.vahitkeskin.kaprekar.di.initKoin
import org.koin.android.ext.koin.androidContext

class KaprekarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@KaprekarApplication)
        }
    }
}
