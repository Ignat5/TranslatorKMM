package com.example.translatorkmm.feature_history.data.data_source

import com.example.translatorkmm.core.domain.util.CommonFlow
import com.example.translatorkmm.core.domain.util.toCommonFlow
import com.example.translatorkmm.database.TranslationDatabase
import com.example.translatorkmm.feature_history.domain.model.TranslationHistoryModel
import com.example.translatorkmm.feature_history.mapper.TranslationHistoryMapper.toTranslationHistoryModel
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map

class DefaultTranslationHistoryDataSource(
    private val db: TranslationDatabase
) : TranslationHistoryDataSource {
    private val queries = db.translationQueries

    override fun readAllHistory(): CommonFlow<List<TranslationHistoryModel>> =
        queries.getAllTranslations()
            .asFlow()
            .mapToList()
            .map { allTranslationHistoryList ->
                allTranslationHistoryList.map { translationHistory ->
                    translationHistory.toTranslationHistoryModel()
                }
            }
            .toCommonFlow()

    override suspend fun saveTranslationHistory(translationHistory: TranslationHistoryModel) {
        queries.insertTranslation(
            translationId = translationHistory.translationId,
            translationFromLangCode = translationHistory.translationFromLangCode,
            translationFromText = translationHistory.translationFromText,
            translationToLangCode = translationHistory.translationToLangCode,
            translationToText = translationHistory.translationToText,
            translationCreatedAt = translationHistory.translationCreatedAt
        )
    }
}