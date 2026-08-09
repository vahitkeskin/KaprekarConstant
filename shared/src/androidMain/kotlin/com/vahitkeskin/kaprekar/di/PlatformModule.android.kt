package com.vahitkeskin.kaprekar.di

import com.vahitkeskin.kaprekar.data.datastore.DATASTORE_FILE_NAME
import com.vahitkeskin.kaprekar.data.datastore.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        createDataStore {
            androidContext().filesDir.resolve(DATASTORE_FILE_NAME).absolutePath
        }
    }
}
