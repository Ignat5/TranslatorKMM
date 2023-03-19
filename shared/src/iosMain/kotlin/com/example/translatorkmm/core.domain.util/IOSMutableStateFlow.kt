package com.example.translatorkmm.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

class IOSMutableStateFlow<T>(
    initValue: T
) : CommonMutableStateFlow<T>(
    MutableStateFlow(initValue)
)