package com.example.translatorkmm.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

expect class CommonMutableStateFlow<T>(
    mutableStateFlow: MutableStateFlow<T>
) : MutableStateFlow<T>