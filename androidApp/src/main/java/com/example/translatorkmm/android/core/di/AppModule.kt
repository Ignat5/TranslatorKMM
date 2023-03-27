package com.example.translatorkmm.android.core.di

import android.content.Context
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
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClientFactory().create()

    @Provides
    @Singleton
    fun provideTranslationClient(
        httpClient: HttpClient
    ): TranslationClient = DefaultTranslationClient(httpClient)

    @Provides
    @Singleton
    fun provideSqlDriver(
        @ApplicationContext context: Context
    ): SqlDriver = DatabaseDriverFactory(context).create()

    @Provides
    @Singleton
    fun provideDatabase(
        sqlDriver: SqlDriver
    ): TranslationDatabase = TranslationDatabase.invoke(sqlDriver)

    @Provides
    @Singleton
    fun provideTranslationDataSource(db: TranslationDatabase): TranslationHistoryDataSource =
        DefaultTranslationHistoryDataSource(db)

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        translationClient: TranslationClient,
        historyDataSource: TranslationHistoryDataSource
    ): TranslateUseCase = DefaultTranslateUseCase(
        translationClient,
        historyDataSource
    )

    @Provides
    @Singleton
    fun provideReadHistoryUseCase(
        historyDataSource: TranslationHistoryDataSource
    ): ReadHistoryUseCase = DefaultReadHistoryUseCase(historyDataSource)

}