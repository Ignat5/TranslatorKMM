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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.translatorkmm.Greeting
import com.example.translatorkmm.android.core.navigation.Routes
import com.example.translatorkmm.android.core.theme.TranslatorAppTheme
import com.example.translatorkmm.android.feature_translation.presentation.AndroidTranslationViewModel
import com.example.translatorkmm.android.feature_translation.presentation.screens.TranslationScreen
import com.example.translatorkmm.feature_translation.presentation.TranslationScreenEvent
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
                onEvent = { event ->
                    when (event) {
                        is TranslationScreenEvent.OnRecordFromTextClick -> {
                            navController.navigate(Routes.VOICE_TO_TEXT_ROUTE + "/${state.fromLanguage.language.langCode}")
                        }
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
        this.composable(
            route = Routes.VOICE_TO_TEXT_ROUTE + "/{languageCode}",
            arguments = listOf(
                navArgument("languageCode") {
                    type = NavType.StringType
                    defaultValue = "en"
                }
            )
        ) {
            Text(text = "voice to text screen")
        }
    }
}
