package com.example.translatorkmm.feature_history.mapper

import com.example.translatorkmm.feature_history.domain.model.TranslationHistoryModel
import database.TranslationHistory

internal object TranslationHistoryMapper {
    fun TranslationHistory.toTranslationHistoryModel() = TranslationHistoryModel(
        translationId = translationId,
        translationFromLangCode = translationFromLangCode,
        translationFromText = translationFromText,
        translationToLangCode = translationToLangCode,
        translationToText = translationToText,
        translationCreatedAt = translationCreatedAt
    )

    fun TranslationHistoryModel.toTranslationHistory() = TranslationHistory(
        translationId = translationId,
        translationFromLangCode = translationFromLangCode,
        translationFromText = translationFromText,
        translationToLangCode = translationToLangCode,
        translationToText = translationToText,
        translationCreatedAt = translationCreatedAt
    )
}