package com.example.kaprekar.di

import com.example.kaprekar.data.datastore.DATASTORE_FILE_NAME
import com.example.kaprekar.data.datastore.createDataStore
import org.koin.dsl.module
import java.io.File

actual fun platformModule() = module {
    single {
        createDataStore {
            val userHome = System.getProperty("user.home") ?: "."
            File(userHome, ".kaprekar/$DATASTORE_FILE_NAME").apply {
                parentFile?.mkdirs()
            }.absolutePath
        }
    }
}
