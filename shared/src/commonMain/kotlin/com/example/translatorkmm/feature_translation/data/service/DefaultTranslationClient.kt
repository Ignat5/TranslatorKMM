package com.example.translatorkmm.feature_translation.data.service

import com.example.translatorkmm.core.domain.model.Language
import com.example.translatorkmm.feature_translation.data.dto.TranslationDto
import com.example.translatorkmm.feature_translation.data.dto.TranslationResultDto
import com.example.translatorkmm.feature_translation.data.util.const.HttpResponseCodes
import com.example.translatorkmm.feature_translation.data.util.const.fullTranslationUrl
import com.example.translatorkmm.feature_translation.domain.TranslationError
import com.example.translatorkmm.feature_translation.domain.TranslationException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*

class DefaultTranslationClient(
    private val httpClient: HttpClient
) : TranslationClient {
    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        val translationResponse = try {
            httpClient.post {
                url(fullTranslationUrl)
                contentType(ContentType.Application.Json)
                setBody(
                    TranslationDto(
                        textToTranslate = fromText,
                        sourceLanguageCode = fromLanguage.langCode,
                        targetLanguageCode = toLanguage.langCode
                    )
                )
            }
        } catch (exception: IOException) {
            throw TranslationException(
                error = TranslationError.SERVER_UNAVAILABLE
            )
        } catch (exception: Exception) {
            throw TranslationException(
                error = TranslationError.UNKNOWN_ERROR
            )
        }
        checkResponse(translationResponse)
        return try {
            translationResponse.body<TranslationResultDto>().translatedText
        } catch (exception: NoTransformationFoundException) {
            throw TranslationException(
                TranslationError.JSON_SERIALIZATION_ERROR
            )
        }
    }

    private fun checkResponse(httpResponse: HttpResponse) {
        when (httpResponse.status.value) {
            in HttpResponseCodes.SUCCESS_RESPONSE.codeRange -> Unit
            in HttpResponseCodes.CLIENT_ERROR_RESPONSE.codeRange -> throw TranslationException(
                TranslationError.CLIENT_ERROR
            )
            in HttpResponseCodes.SERVER_ERROR_RESPONSE.codeRange -> throw TranslationException(
                TranslationError.SERVER_ERROR
            )
            else -> throw TranslationException(
                TranslationError.UNKNOWN_ERROR
            )
        }
    }
}