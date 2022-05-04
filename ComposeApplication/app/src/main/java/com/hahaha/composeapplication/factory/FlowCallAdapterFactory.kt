package com.hahaha.composeapplication.factory

import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class FlowCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        takeIf { getRawType(returnType) != Flow::class.java }?.run {
            return null
        }
        check(returnType is ParameterizedType) { " Flow return type error, need like as Flow<Bean> " }

        val responseType = getParameterUpperBound(0, returnType)
        val rawFlowType = getRawType(responseType)
        return if (rawFlowType == Response::class.java) {
            check(responseType is ParameterizedType) { " Response return  error, need like as Response<Bean>" }
            ResponseCallAdapter<Any>(getParameterUpperBound(0, responseType))
        } else {
            BodyCallAdapter<Any>(responseType)
        }
    }

    companion object{
        fun create() = FlowCallAdapterFactory()
    }
}