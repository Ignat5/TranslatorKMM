package com.example.translatorkmm.feature_translation.presentation

import com.example.translatorkmm.core.presentation.UILanguage
import com.example.translatorkmm.feature_history.presentation.UITranslationHistory
import com.example.translatorkmm.feature_translation.domain.TranslationError

data class TranslationScreenState(
    val fromText: String = "",
    val toText: String? = null,
    val fromLanguage: UILanguage = UILanguage.byCode("en"),
    val toLanguage: UILanguage = UILanguage.byCode("de"),
    val isTranslating: Boolean = false,
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslationError? = null,
    val historyList: List<UITranslationHistory> = emptyList()
)
