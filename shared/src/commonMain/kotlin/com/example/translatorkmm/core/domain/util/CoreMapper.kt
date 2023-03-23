package com.example.translatorkmm.core.domain.util

import com.example.translatorkmm.core.presentation.UILanguage
import com.example.translatorkmm.feature_history.domain.model.TranslationHistoryModel
import com.example.translatorkmm.feature_history.presentation.UITranslationHistory

object CoreMapper {
    fun TranslationHistoryModel.toUITranslationHistory() = UITranslationHistory(
        translationId = this.translationId,
        translationFromLanguage = UILanguage.byCode(this.translationFromLangCode),
        translationFromText = this.translationFromText,
        translationToLanguage = UILanguage.byCode(this.translationToLangCode),
        translationToText = this.translationToText,
        translationCreatedAt = this.translationCreatedAt
    )
}