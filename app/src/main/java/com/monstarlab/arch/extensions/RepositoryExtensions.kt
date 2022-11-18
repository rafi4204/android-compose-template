package com.monstarlab.arch.extensions

import com.monstarlab.core.domain.error.toError
import retrofit2.Response

inline fun <T> repoCall(
    block: () -> Response<T>
): T {
    val response = block()
    val body = response.body()
    return when (response.isSuccessful && body != null) {
        true -> body
        false -> throw response.toError()
    }
}

inline fun <T, R> Response<T>.mapSuccess(
    crossinline block: (T) -> R
): R {
    val safeBody = body()
    if (this.isSuccessful && safeBody != null) {
        return block(safeBody)
    } else {
        throw toError()
    }
}
