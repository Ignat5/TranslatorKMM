package com.example.translatorkmm.core.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

actual open class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> by flow {
    fun subscribe(
        coroutineScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        onCollect: (value: T) -> Unit
    ): IOSDisposableHandle {
        val job = coroutineScope.launch(dispatcher) {
            flow.collect { value ->
                onCollect(value)
            }
        }
        return IOSDisposableHandle {
            job.cancel()
        }
    }
}