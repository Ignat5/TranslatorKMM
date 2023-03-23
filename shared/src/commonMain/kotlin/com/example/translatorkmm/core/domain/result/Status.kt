package com.example.translatorkmm.core.domain.result

sealed interface Status {
    object Success : Status
    class Error(val errorMessage: String) : Status
}