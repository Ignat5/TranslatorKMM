package com.example.translatorkmm.core.presentation

import com.example.translatorkmm.core.domain.model.Language

actual class UILanguage(
    actual val language: Language,
    val imageName: String
) {
    actual companion object {
        actual fun byCode(langCode: String): UILanguage {
            return allLanguages.find { it.language.langCode == langCode } ?: throw IllegalArgumentException("UILanguage..byCode")
        }
        actual val allLanguages = Language.values().map { language ->
            UILanguage(
                language = language,
                imageName = language.langName.lowercase()
            )
        }
    }
}