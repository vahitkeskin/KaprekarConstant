package com.example.kaprekar.di

import com.example.kaprekar.data.datastore.DATASTORE_FILE_NAME
import com.example.kaprekar.data.datastore.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        createDataStore {
            androidContext().filesDir.resolve(DATASTORE_FILE_NAME).absolutePath
        }
    }
}
