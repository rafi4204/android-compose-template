package com.monstarlab.arch.extensions

import com.monstarlab.core.domain.error.ErrorModel
import com.monstarlab.core.domain.error.toError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import timber.log.Timber

sealed class UseCaseResult<out T> {
    data class Success<T>(val value: T) : UseCaseResult<T>()
    data class Error(val error: ErrorModel) : UseCaseResult<Nothing>()
}

suspend inline fun <T> safeUseCase(
    crossinline block: suspend () -> T,
): UseCaseResult<T> = try {
    UseCaseResult.Success(block())
} catch (e: ErrorModel) {
    UseCaseResult.Error(e.toError())
}

@Suppress("TooGenericExceptionCaught")
inline fun <T> useCaseFlow(
    coroutineDispatcher: CoroutineDispatcher,
    crossinline block: suspend () -> T,

): Flow<UseCaseResult<T>> = flow {
    try {
        val repoResult = block()
        emit(UseCaseResult.Success(repoResult))
    } catch (e: ErrorModel) {
        emit(UseCaseResult.Error(e))
    } catch (e: Exception) {
        emit(UseCaseResult.Error(e.toError()))
    }
}.flowOn(coroutineDispatcher)

fun <T> observableFlow(block: suspend FlowCollector<T>.() -> Unit): Flow<UseCaseResult<T>> =
    flow(block)
        .catch { exception ->
            Timber.e(exception)
            UseCaseResult.Error(exception.toError())
        }
        .map {
            UseCaseResult.Success(it)
        }

fun <T> Flow<UseCaseResult<T>>.onSuccess(action: suspend (T) -> Unit): Flow<UseCaseResult<T>> =
    transform { result ->
        if (result is UseCaseResult.Success<T>) {
            action(result.value)
        }
        return@transform emit(result)
    }

fun <T> Flow<UseCaseResult<T>>.onError(action: suspend (ErrorModel) -> Unit): Flow<UseCaseResult<T>> =
    transform { result ->
        if (result is UseCaseResult.Error) {
            action(result.error)
        }
        return@transform emit(result)
    }
