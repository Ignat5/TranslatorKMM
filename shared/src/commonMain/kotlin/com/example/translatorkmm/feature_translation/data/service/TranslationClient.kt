package com.example.translatorkmm.feature_translation.data.service

import com.example.translatorkmm.core.domain.model.Language

interface TranslationClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}