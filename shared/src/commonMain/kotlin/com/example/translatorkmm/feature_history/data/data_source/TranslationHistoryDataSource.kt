package com.example.translatorkmm.feature_history.data.data_source

import com.example.translatorkmm.core.domain.util.CommonFlow
import com.example.translatorkmm.feature_history.domain.model.TranslationHistoryModel

interface TranslationHistoryDataSource {
    fun readAllHistory(): CommonFlow<List<TranslationHistoryModel>>
    suspend fun saveTranslationHistory(translationHistory: TranslationHistoryModel)
}