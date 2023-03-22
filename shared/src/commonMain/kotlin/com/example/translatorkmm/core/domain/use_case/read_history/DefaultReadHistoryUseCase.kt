package com.example.translatorkmm.core.domain.use_case.read_history

import com.example.translatorkmm.core.domain.util.CommonFlow
import com.example.translatorkmm.feature_history.data.data_source.TranslationHistoryDataSource
import com.example.translatorkmm.feature_history.domain.model.TranslationHistoryModel

class DefaultReadHistoryUseCase(
    private val historyDataSource: TranslationHistoryDataSource
) : ReadHistoryUseCase {
    override operator fun invoke(): CommonFlow<List<TranslationHistoryModel>> =
        historyDataSource.readAllHistory()
}