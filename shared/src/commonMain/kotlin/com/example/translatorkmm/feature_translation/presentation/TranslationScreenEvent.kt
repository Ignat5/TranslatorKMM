package com.example.translatorkmm.feature_translation.presentation

import com.example.translatorkmm.core.presentation.UILanguage
import com.example.translatorkmm.feature_history.presentation.UITranslationHistory

sealed class TranslationScreenEvent {
    object OnTranslateClick : TranslationScreenEvent()
    object OnChooseFromLanguageClick : TranslationScreenEvent()
    object OnChooseToLanguageClick : TranslationScreenEvent()
    object OnExitChooseLanguageClick : TranslationScreenEvent()
    data class OnFromLanguageChosen(val language: UILanguage) : TranslationScreenEvent()
    data class OnToLanguageChosen(val language: UILanguage) : TranslationScreenEvent()
    data class OnTranslateFromTextChange(val text: String) : TranslationScreenEvent()
    object OnSwapLanguagesClick : TranslationScreenEvent()
    object OnCloseTranslationClick : TranslationScreenEvent()
//    object OnChangeFromText : TranslationScreenEvent()
    object OnErrorSeen : TranslationScreenEvent()
    object OnRecordFromTextClick : TranslationScreenEvent()
    data class OnRecordingResultSubmit(val result: String?) : TranslationScreenEvent()
    data class OnTranslationHistoryItemClick(val translationHistory: UITranslationHistory) :
        TranslationScreenEvent()
}
