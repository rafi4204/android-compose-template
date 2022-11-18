package com.monstarlab.core.domain.error

sealed class ErrorModel : Throwable() {

    sealed class Http : ErrorModel() {
        object BadRequest : Http()
        object Unauthorized : Http()
        object Forbidden : Http()
        object NotFound : Http()
        object MethodNotAllowed : Http()

        object ServerError : Http()

        data class Custom(
            val code: Int?,
            override val message: String?,
            val errorBody: String?
        ) : Http()
    }

    sealed class DataError : ErrorModel() {
        object NoData : DataError()
        data class ParseError(val throwable: Throwable) : DataError()
    }

    sealed class Connection : ErrorModel() {
        object Timeout : Connection()
        object IOError : Connection()
        object UnknownHost : Connection()
    }

    data class Unknown(val throwable: Throwable) : ErrorModel()
}
