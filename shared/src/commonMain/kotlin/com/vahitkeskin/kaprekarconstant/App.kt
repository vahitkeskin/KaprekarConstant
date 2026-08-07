package com.vahitkeskin.kaprekarconstant

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.di.appModule
import com.example.kaprekar.presentation.ui.KaprekarScreen
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        MaterialTheme {
            KaprekarScreen()
        }
    }
}