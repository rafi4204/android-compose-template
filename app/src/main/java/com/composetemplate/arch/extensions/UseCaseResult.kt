package com.composetemplate.arch.extensions

import com.composetemplate.core.domain.error.ExceptionModel
import com.composetemplate.core.domain.error.toException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import timber.log.Timber

sealed class UseCaseResult<out T> {
    data class Success<T>(val value: T) : UseCaseResult<T>()
    data class Error(val exception: ExceptionModel) : UseCaseResult<Nothing>()
}

suspend inline fun <T> safeUseCase(
    crossinline block: suspend () -> T,
): UseCaseResult<T> = try {
    UseCaseResult.Success(block())
} catch (e: ExceptionModel) {
    UseCaseResult.Error(e.toException())
}

@Suppress("TooGenericExceptionCaught")
inline fun <T> useCaseFlow(
    coroutineDispatcher: CoroutineDispatcher,
    crossinline block: suspend () -> T,
): Flow<UseCaseResult<T>> = flow {
    try {
        val repoResult = block()
        emit(UseCaseResult.Success(repoResult))
    } catch (e: ExceptionModel) {
        emit(UseCaseResult.Error(e))
    } catch (e: Exception) {
        emit(UseCaseResult.Error(e.toException()))
    }
}.flowOn(coroutineDispatcher)

@Suppress("TooGenericExceptionCaught")
inline fun useCaseWithoutBodyFlow(
    coroutineDispatcher: CoroutineDispatcher,
    crossinline block: suspend () -> Unit,
): Flow<UseCaseResult<Unit>> = flow {
    try {
        val repoResult = block()
        emit(UseCaseResult.Success(repoResult))
    } catch (e: ExceptionModel) {
        emit(UseCaseResult.Error(e))
    } catch (e: Exception) {
        emit(UseCaseResult.Error(e.toException()))
    }
}.flowOn(coroutineDispatcher)



fun <T> observableFlow(block: suspend FlowCollector<T>.() -> Unit): Flow<UseCaseResult<T>> =
    flow(block)
        .catch { exception ->
            Timber.e(exception)
            UseCaseResult.Error(exception.toException())
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

fun <T> Flow<UseCaseResult<T>>.onError(action: suspend (ExceptionModel) -> Unit): Flow<UseCaseResult<T>> =
    transform { result ->
        if (result is UseCaseResult.Error) {
            action(result.exception)
        }
        return@transform emit(result)
    }