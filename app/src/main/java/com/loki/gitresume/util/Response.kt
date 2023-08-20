package com.loki.gitresume.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

 inline fun <T> response(crossinline api: suspend () -> T): Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading(null))
        val data = api()
        emit(Resource.Success(data = data))
    }
    catch (e: IOException) {
        emit(Resource.Error(message = "Check your network connection"))
    }
    catch (e: HttpException) {
        emit(Resource.Error(message = "Something went wrong"))
    }
}