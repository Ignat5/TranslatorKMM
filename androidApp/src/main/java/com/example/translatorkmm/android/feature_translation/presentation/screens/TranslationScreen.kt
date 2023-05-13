package com.example.translatorkmm.android.feature_translation.presentation.screens

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.example.translatorkmm.android.R
import com.example.translatorkmm.android.feature_translation.presentation.components.*
import com.example.translatorkmm.feature_translation.domain.TranslationError
import com.example.translatorkmm.feature_translation.presentation.TranslationScreenEvent
import com.example.translatorkmm.feature_translation.presentation.TranslationScreenState
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslationScreen(
    state: TranslationScreenState,
    onEvent: (event: TranslationScreenEvent) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.error) {
        val message = when (state.error) {
            TranslationError.SERVER_UNAVAILABLE -> "Server is currently unavailable"
            TranslationError.CLIENT_ERROR, TranslationError.JSON_SERIALIZATION_ERROR -> "Client error occurred"
            TranslationError.SERVER_ERROR -> "Server error occurred"
            TranslationError.UNKNOWN_ERROR -> "Unknown error occurred"
            else -> null
        }
        message?.let {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            onEvent(TranslationScreenEvent.OnErrorSeen)
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(TranslationScreenEvent.OnRecordFromTextClick)
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(75.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                    contentDescription = "recorde audio"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
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
                    SwapLanguagesButton(
                        onClick = {
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
            item {
                val clipboardManager = LocalClipboardManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                val tts = rememberTextToSpeech()
                TranslationTextField(
                    fromText = state.fromText,
                    toText = state.toText,
                    isTranslating = state.isTranslating,
                    fromLanguage = state.fromLanguage,
                    toLanguage = state.toLanguage,
                    onTranslateClick = {
                        keyboardController?.hide()
                        onEvent(TranslationScreenEvent.OnTranslateClick)
                    },
                    onTextChange = {
                        onEvent(TranslationScreenEvent.OnTranslateFromTextChange(it))
                    },
                    onCopyClick = {
                        clipboardManager.setText(
                            buildAnnotatedString {
                                append(it)
                            }
                        )
                        Toast.makeText(context, "text copied", Toast.LENGTH_SHORT).show()
                    },
                    onCloseClick = {
                        onEvent(TranslationScreenEvent.OnCloseTranslationClick)
                    },
                    onSpeakerClick = {
                        tts.language = state.toLanguage.toLocale() ?: Locale.ENGLISH
                        tts.speak(
                            state.toText,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    },
                    onTextFieldClick = {
                        onEvent(TranslationScreenEvent.OnEditTranslationClick)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                if (state.historyList.isNotEmpty()) {
                    Text(
                        text = "History",
                        style = MaterialTheme.typography.h2
                    )
                }
            }
            items(state.historyList) { item ->
                TranslationHistoryItem(
                    item = item,
                    onClick = {
                        onEvent(
                            TranslationScreenEvent.OnTranslationHistoryItemClick(item)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}