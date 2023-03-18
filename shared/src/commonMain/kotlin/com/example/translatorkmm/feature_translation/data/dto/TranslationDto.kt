package com.example.translatorkmm.feature_translation.data.dto

import kotlinx.serialization.SerialName

private const val TEXT_TO_TRANSLATE = "q"
private const val SOURCE_LANGUAGE_CODE = "source"
private const val TARGET_LANGUAGE_CODE = "target"

@kotlinx.serialization.Serializable
internal data class TranslationDto(
    @SerialName(TEXT_TO_TRANSLATE)
    val textToTranslate: String,
    @SerialName(SOURCE_LANGUAGE_CODE)
    val sourceLanguageCode: String,
    @SerialName(TARGET_LANGUAGE_CODE)
    val targetLanguageCode: String
)
