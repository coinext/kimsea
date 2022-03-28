package io.tommy.kimsea.web.utils

import kotlinx.coroutines.*

object AsyncDispatcher {

    fun <T> asAsync(coroutineDispatcher: CoroutineDispatcher, call: () -> T): Deferred<T> = CoroutineScope(coroutineDispatcher).async {
        yield()
        call()
    }

    fun <T> asAsync(call: () -> T) = this.asAsync(Dispatchers.Default) { call() }
}