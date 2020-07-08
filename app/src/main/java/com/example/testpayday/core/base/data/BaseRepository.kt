package com.example.testpayday.core.base.data

import com.example.testpayday.core.base.data.network.Result
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> execute(block: suspend () -> Deferred<Response<T>>): Result<T> {
        return try {
            val x = block()
            calculateResult(x.await())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun <T : Any> calculateResult(data: Response<T>): Result<T> {
        return data.code().takeIf {
            it !in CODE_SUCCESS_START..CODE_SUCCESS_END
        }?.let {
            Result.Error(IllegalArgumentException("${it}: ${data.errorBody()?.string()}"))
        } ?: run {
            data.body()?.let {
                Result.Success(it)
            } ?: run {
                Result.Error(NullPointerException("Response is empty"))
            }
        }
    }

    private suspend fun <T : Any> executeResult(
        call: suspend () -> Deferred<Response<T>>,
        errorMessage: String
    ): Result<T> {
        val response = call.invoke().await()
        if (response.isSuccessful) return Result.Success(response.body()!!)

        return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }

    companion object {
        private const val CODE_SUCCESS_START = 200
        private const val CODE_SUCCESS_END = 299
    }
}
