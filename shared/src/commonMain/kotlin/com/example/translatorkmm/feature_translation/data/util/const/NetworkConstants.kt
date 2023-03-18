package com.example.translatorkmm.feature_translation.data.util.const

// url
internal const val BASE_URL = "https://translate.pl-coding.com"
internal const val TRANSLATION_URL = "/translate"

// ext
internal val fullTranslationUrl by lazy {
    BASE_URL + TRANSLATION_URL
}

fun test() {
    val a = 200..299
}

// code
enum class HttpResponseCodes(
    val codeRange: IntRange
) {
    SUCCESS_RESPONSE(200..299),
    SERVER_ERROR_RESPONSE(500..500),
    CLIENT_ERROR_RESPONSE(400..499),
}