package com.example.translatorkmm.feature_history.domain.model

data class TranslationHistoryModel(
    val translationId: String,
    val translationFromLangCode: String,
    val translationFromText: String,
    val translationToLangCode: String,
    val translationToText: String,
    val translationCreatedAt: Long
)
