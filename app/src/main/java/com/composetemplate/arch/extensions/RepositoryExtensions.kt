package com.composetemplate.arch.extensions

import com.composetemplate.core.domain.error.toException
import retrofit2.Response

inline fun <T> repoCall(
    block: () -> Response<T>
): T {
    val response = block()
    val body = response.body()
    if (response.isSuccessful && body != null) {
        return body
    } else {
        throw response.toException()
    }
}

inline fun <T, R> Response<T>.mapSuccess(
    crossinline block: (T) -> R
): R {
    val safeBody = body()
    if (this.isSuccessful && safeBody != null) {
        return block(safeBody)
    } else {
        throw toException()
    }
}