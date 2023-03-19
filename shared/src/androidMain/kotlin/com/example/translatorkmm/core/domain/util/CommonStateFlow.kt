package com.example.translatorkmm.core.domain.util

import kotlinx.coroutines.flow.StateFlow

actual class CommonStateFlow<T> actual constructor(
    private val stateFlow: StateFlow<T>
) : StateFlow<T> by stateFlow

fun <T> StateFlow<T>.toCommonStateFlow() = CommonStateFlow(this)