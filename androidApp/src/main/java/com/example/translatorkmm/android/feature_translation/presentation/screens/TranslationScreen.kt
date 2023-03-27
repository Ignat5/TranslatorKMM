package com.example.translatorkmm.android.feature_translation.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.translatorkmm.android.feature_translation.presentation.components.LanguageDropDown
import com.example.translatorkmm.android.feature_translation.presentation.components.SwapLanguagesButton
import com.example.translatorkmm.feature_translation.presentation.TranslationScreenEvent
import com.example.translatorkmm.feature_translation.presentation.TranslationScreenState

@Composable
fun TranslationScreen(
    state: TranslationScreenState,
    onEvent: (event: TranslationScreenEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {

        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LanguageDropDown(
                        currentLanguage = state.fromLanguage,
                        isMenuOpen = state.isChoosingFromLanguage,
                        onClick = {
                            onEvent(TranslationScreenEvent.OnChooseFromLanguageClick)
                        },
                        onDismiss = {
                            onEvent(TranslationScreenEvent.OnExitChooseLanguageClick)
                        },
                        onSelectLanguage = { language ->
                            onEvent(TranslationScreenEvent.OnFromLanguageChosen(language))
                        },
                        modifier = Modifier.weight(2f, true)
                    )
                    SwapLanguagesButton(onClick = {
                        onEvent(TranslationScreenEvent.OnSwapLanguagesClick)
                    },
                        modifier = Modifier.weight(1f, true)
                    )
                    LanguageDropDown(
                        currentLanguage = state.toLanguage,
                        isMenuOpen = state.isChoosingToLanguage,
                        onClick = {
                            onEvent(TranslationScreenEvent.OnChooseToLanguageClick)
                        },
                        onDismiss = {
                            onEvent(TranslationScreenEvent.OnExitChooseLanguageClick)
                        },
                        onSelectLanguage = { language ->
                            onEvent(TranslationScreenEvent.OnToLanguageChosen(language))
                        },
                        modifier = Modifier.weight(2f, true)
                    )
                }
            }
        }
    }
}