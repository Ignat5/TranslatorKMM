package com.example.translatorkmm.core.domain.use_case.translate

import com.example.translatorkmm.core.domain.model.Language
import com.example.translatorkmm.core.domain.result.ResultModel
import com.example.translatorkmm.feature_history.data.data_source.TranslationHistoryDataSource
import com.example.translatorkmm.feature_history.domain.model.TranslationHistoryModel
import com.example.translatorkmm.feature_translation.data.service.TranslationClient
import com.example.translatorkmm.feature_translation.domain.TranslationException
import kotlinx.datetime.Clock

class DefaultTranslateUseCase(
    private val translationClient: TranslationClient,
    private val translationHistoryDataSource: TranslationHistoryDataSource
) : TranslateUseCase {
    override suspend operator fun invoke(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): ResultModel<TranslationHistoryModel> {
        return try {
            val translationToResult = translationClient.translate(
                fromLanguage = fromLanguage,
                fromText = fromText,
                toLanguage = toLanguage
            )
            val timestamp = Clock.System.now().epochSeconds
            val translationHistoryModel = TranslationHistoryModel(
                translationId = timestamp.toString(),
                translationFromLangCode = fromLanguage.langCode,
                translationFromText = fromText,
                translationToLangCode = toLanguage.langCode,
                translationToText = translationToResult,
                translationCreatedAt = timestamp
            )
            translationHistoryDataSource.saveTranslationHistory(translationHistoryModel)
            ResultModel.Success(translationHistoryModel)
        } catch (exception: TranslationException) {
            exception.printStackTrace()
            ResultModel.Error(exception)
        } catch (exception: Exception) {
            exception.printStackTrace()
            ResultModel.Error(exception)
        }
    }
}