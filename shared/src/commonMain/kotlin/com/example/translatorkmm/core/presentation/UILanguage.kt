package com.example.translatorkmm.core.presentation

import com.example.translatorkmm.core.domain.model.Language

expect class UILanguage {
    val language: Language
    companion object {
        val allLanguages: List<UILanguage>
        fun byCode(langCode: String): UILanguage
    }
}