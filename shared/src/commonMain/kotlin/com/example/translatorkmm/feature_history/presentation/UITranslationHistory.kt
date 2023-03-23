package com.example.translatorkmm.feature_history.presentation

import com.example.translatorkmm.core.presentation.UILanguage

data class UITranslationHistory(
    val translationId: String,
    val translationFromLanguage: UILanguage,
    val translationFromText: String,
    val translationToLanguage: UILanguage,
    val translationToText: String,
    val translationCreatedAt: Long
)
