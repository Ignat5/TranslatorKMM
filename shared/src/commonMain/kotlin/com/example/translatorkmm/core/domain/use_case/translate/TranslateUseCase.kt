package com.example.translatorkmm.core.domain.use_case.translate

import com.example.translatorkmm.core.domain.model.Language
import com.example.translatorkmm.core.domain.result.ResultModel
import com.example.translatorkmm.feature_history.domain.model.TranslationHistoryModel

interface TranslateUseCase {
    suspend operator fun invoke(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): ResultModel<TranslationHistoryModel>
}