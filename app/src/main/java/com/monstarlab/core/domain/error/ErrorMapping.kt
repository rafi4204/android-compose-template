package com.monstarlab.core.domain.error

import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Suppress("MagicNumber")
fun <T> Response<T>.toError(): ErrorModel.Http {
    return when {
        code() == 400 -> ErrorModel.Http.BadRequest
        code() == 401 -> ErrorModel.Http.Unauthorized
        code() == 403 -> ErrorModel.Http.Forbidden
        code() == 404 -> ErrorModel.Http.NotFound
        code() == 405 -> ErrorModel.Http.MethodNotAllowed
        code() in 500..600 -> ErrorModel.Http.ServerError
        else -> ErrorModel.Http.Custom(
            code(),
            message(),
            errorBody()?.string()
        )
    }
}

fun Throwable.toError(): ErrorModel {
    return when (this) {
        is SocketTimeoutException -> ErrorModel.Connection.Timeout
        is UnknownHostException -> ErrorModel.Connection.UnknownHost
        is IOException -> ErrorModel.Connection.IOError
        is SerializationException -> ErrorModel.DataError.ParseError(this)
        else -> ErrorModel.Unknown(this)
    }
}
