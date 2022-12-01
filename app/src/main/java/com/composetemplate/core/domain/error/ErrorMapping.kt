package com.composetemplate.core.domain.error


import com.google.gson.Gson
import com.composetemplate.core.data.network.dtos.ErrorDto
import kotlinx.serialization.SerializationException
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Suppress("MagicNumber")
fun <T> Response<T>.toException(): ExceptionModel.Http {
    return when {
        code() == 400 -> ExceptionModel.Http.BadRequest
        code() == 401 -> ExceptionModel.Http.Unauthorized
        code() == 403 -> ExceptionModel.Http.Forbidden
        code() == 404 -> ExceptionModel.Http.NotFound
        code() == 405 -> ExceptionModel.Http.MethodNotAllowed
        code() in 500..600 -> ExceptionModel.Http.ServerException
        else -> ExceptionModel.Http.Custom(
            code(),
            message(),
            convertToErrorDto(errorBody())?.message
        )
    }
}

@Suppress("TooGenericExceptionThrown", "TooGenericExceptionCaught")
fun convertToErrorDto(errorBody: ResponseBody?): ErrorDto? {
    return try {
        Gson().fromJson(
            errorBody?.string(),
            ErrorDto::class.java
        )
    } catch (e: Exception) {
        throw Throwable("Unknown error")
    }
}


fun Throwable.toException(): ExceptionModel {
    return when (this) {
        is SocketTimeoutException -> ExceptionModel.Connection.Timeout
        is UnknownHostException -> ExceptionModel.Connection.UnknownHost
        is IOException -> ExceptionModel.Connection.IOError
        is SerializationException -> ExceptionModel.DataException.ParseException(this)
        else -> ExceptionModel.Unknown(this)
    }
}