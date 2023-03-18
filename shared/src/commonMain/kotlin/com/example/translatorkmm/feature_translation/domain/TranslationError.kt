package com.example.translatorkmm.feature_translation.domain

enum class TranslationError {
    SERVER_UNAVAILABLE,
    SERVER_ERROR,
    CLIENT_ERROR,
    UNKNOWN_ERROR,
    JSON_SERIALIZATION_ERROR
}

class TranslationException(
    val error: TranslationError
): Exception(
    "An error occurred: error: $error"
)