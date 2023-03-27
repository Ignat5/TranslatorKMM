package com.example.translatorkmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.translatorkmm.Greeting
import com.example.translatorkmm.android.core.navigation.Routes
import com.example.translatorkmm.android.core.theme.TranslatorAppTheme
import com.example.translatorkmm.android.feature_translation.presentation.AndroidTranslationViewModel
import com.example.translatorkmm.android.feature_translation.presentation.screens.TranslationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorAppTheme {
                TranslationRoot()
            }
        }
    }
}

@Composable
fun TranslationRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.TRANSLATION_ROUTE) {
        this.composable(Routes.TRANSLATION_ROUTE) {
            val viewModel = hiltViewModel<AndroidTranslationViewModel>()
            val state by viewModel.state.collectAsState()
            TranslationScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}
