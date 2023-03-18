package com.example.translatorkmm.feature_translation.data.service

import io.ktor.client.*

expect class HttpClientFactory {
    fun create(): HttpClient
}