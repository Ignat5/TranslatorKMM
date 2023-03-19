package com.example.translatorkmm.core.domain.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalCoroutinesApi::class)
actual open class CommonMutableStateFlow<T> actual constructor(
    private val mutableStateFlow: MutableStateFlow<T>
) : CommonStateFlow<T>(mutableStateFlow), MutableStateFlow<T> {

    override var value: T
        get() = super.value
        set(value) {
            mutableStateFlow.value = value
        }

    override val subscriptionCount: StateFlow<Int>
        get() = mutableStateFlow.subscriptionCount

    override fun resetReplayCache() {
        mutableStateFlow.resetReplayCache()
    }

    override fun compareAndSet(expect: T, update: T): Boolean =
        mutableStateFlow.compareAndSet(expect, update)

    override suspend fun emit(value: T) {
        mutableStateFlow.emit(value)
    }

    override fun tryEmit(value: T): Boolean =
        mutableStateFlow.tryEmit(value)

}