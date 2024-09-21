package com.example.coroutine

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resumeWithException

suspend fun <T> Call<T>.await():T = suspendCancellableCoroutine {
    continuation ->
    continuation.invokeOnCancellation {
        cancel()
    }
//在retrofit内部执行后回调匿名内部类中的复习方法，包含成功和失败的方法。
    enqueue(object: Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            //takeIf有什么作用？takeIf{}和takeIf()有什么区别？
            //takeIf筛选里面是否满足条件，满足返回之前的对象，不满足返回null
            response.takeIf { it.isSuccessful }
        }

    })
}