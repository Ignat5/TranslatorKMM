package com.example.translatorkmm.di

import com.example.translatorkmm.core.domain.use_case.read_history.DefaultReadHistoryUseCase
import com.example.translatorkmm.core.domain.use_case.read_history.ReadHistoryUseCase
import com.example.translatorkmm.core.domain.use_case.translate.DefaultTranslateUseCase
import com.example.translatorkmm.core.domain.use_case.translate.TranslateUseCase
import com.example.translatorkmm.database.TranslationDatabase
import com.example.translatorkmm.feature_history.data.data_source.DefaultTranslationHistoryDataSource
import com.example.translatorkmm.feature_history.data.data_source.TranslationHistoryDataSource
import com.example.translatorkmm.feature_history.data.db.DatabaseDriverFactory
import com.example.translatorkmm.feature_translation.data.service.DefaultTranslationClient
import com.example.translatorkmm.feature_translation.data.service.HttpClientFactory
import com.example.translatorkmm.feature_translation.data.service.TranslationClient

object AppModule {

    val translateUseCase: TranslateUseCase by lazy {
        DefaultTranslateUseCase(
            translationClient = translationClient,
            translationHistoryDataSource = translationHistoryDataSource
        )
    }

    val readHistoryUseCase: ReadHistoryUseCase by lazy {
        DefaultReadHistoryUseCase(
            historyDataSource = translationHistoryDataSource
        )
    }

    private val translationClient: TranslationClient by lazy {
        DefaultTranslationClient(
            httpClient = HttpClientFactory().create()
        )
    }

    private val translationHistoryDataSource: TranslationHistoryDataSource by lazy {
        DefaultTranslationHistoryDataSource(
            db = TranslationDatabase(
                driver = DatabaseDriverFactory().create()
            )
        )
    }

}