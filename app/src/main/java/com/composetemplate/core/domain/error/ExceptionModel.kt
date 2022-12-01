package com.composetemplate.core.domain.error

import java.lang.Exception

sealed class ExceptionModel : Exception() {

    sealed class Http : ExceptionModel() {
        object BadRequest : Http()
        object Unauthorized : Http()
        object Forbidden : Http()
        object NotFound : Http()
        object MethodNotAllowed : Http()

        object ServerException : Http()

        data class Custom(
            val code: Int?,
            override val message: String?,
            val errorBody: String?
        ) : Http()
    }

    sealed class DataException : ExceptionModel() {
        object NoData : DataException()
        data class ParseException(val throwable: Throwable) : DataException()
    }

    sealed class Connection : ExceptionModel() {
        object Timeout : Connection()
        object IOError : Connection()
        object UnknownHost : Connection()
    }

    sealed class FirebaseAuthException : ExceptionModel() {
        data class SendOtpException(val errorCause: String) : FirebaseAuthException()
        data class VerifyOtpException(val errorCause: String) : FirebaseAuthException()
        data class OtpExpireException(val errorCause: String) : FirebaseAuthException()
        data class InvalidOtpException(val errorCause: String) : FirebaseAuthException()
    }

    data class Unknown(val throwable: Throwable) : ExceptionModel()
}