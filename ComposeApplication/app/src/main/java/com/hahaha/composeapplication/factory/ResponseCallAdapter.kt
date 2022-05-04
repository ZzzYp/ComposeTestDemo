package com.hahaha.composeapplication.factory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ResponseCallAdapter<T>(private val responseType: Type) : CallAdapter<T, Flow<Response<T>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<T>): Flow<Response<T>> {
        return flow {
            emit(suspendCancellableCoroutine { continuation ->
                call.enqueue(object : Callback<T> {
                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        continuation.resume(response)
                    }

                    override fun onFailure(call: Call<T>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
                continuation.invokeOnCancellation { call.cancel() }
            })
        }
    }

}