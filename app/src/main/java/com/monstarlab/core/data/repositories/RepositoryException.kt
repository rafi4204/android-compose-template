package com.monstarlab.core.data.repositories

import retrofit2.Response

data class RepositoryException(
    val code: Int,
    val errorBody: String?,
    val msg: String
) : RuntimeException(msg)

fun <T> Response<T>.mapToRepositoryException(): RepositoryException {
    return RepositoryException(
        code = code(),
        errorBody = errorBody()?.string(),
        msg = message()
    )
}
