package com.example.coroutine

import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun main() {
    val result = CompletableFuture.supplyAsync {
        3
    }.await()

    log(result)
}

suspend fun <T> CompletableFuture<T>.await(): T {
    //isDone用来判断CompletableFuture是否已经完成
    if(isDone){
        try {
            return get()
        } catch (e: ExecutionException) {
            throw e.cause ?: e
        }
    }
    return suspendCancellableCoroutine {

            cancellableContinuation ->
        cancellableContinuation.invokeOnCancellation {
            cancel(true)
        }

        whenComplete { value, throwable ->
            if(throwable == null){
                cancellableContinuation.resume(value)
            } else {
                cancellableContinuation.resumeWithException(throwable.cause ?: throwable)
            }
        }
    }
}