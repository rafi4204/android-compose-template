// Package set to androidx.lifecycle so we can have access to package private methods

@file:Suppress("PackageDirectoryMismatch")

package androidx.lifecycle


import com.composetemplate.arch.extensions.*
import com.composetemplate.core.domain.error.ErrorMessage
import com.composetemplate.core.sharedui.errorhandling.ViewError
import com.composetemplate.core.sharedui.errorhandling.mapToViewError

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

private const val ERROR_FLOW_KEY = "androidx.lifecycle.ErrorFlow"
private const val LOADING_FLOW_KEY = "androidx.lifecycle.LoadingFlow"
val errorMessage by lazy { MutableStateFlow(ErrorMessage(-1, "")) }

fun <T> T.sendViewError(viewError: ViewError) where T : ViewErrorAware, T : ViewModel {
    viewModelScope.launch {
        getErrorMutableSharedFlow().emit(viewError)
    }
}

suspend fun <T> T.emitViewError(viewError: ViewError) where T : ViewErrorAware, T : ViewModel {
    getErrorMutableSharedFlow().emit(viewError)
}

val <T> T.viewErrorFlow: SharedFlow<ViewError> where T : ViewErrorAware, T : ViewModel
    get() {
        return getErrorMutableSharedFlow()
    }


val <T> T.loadingFlow: StateFlow<Boolean> where T : LoadingAware, T : ViewModel
    get() {
        return loadingMutableStateFlow
    }

var <T> T.isLoading: Boolean where T : LoadingAware, T : ViewModel
    get() {
        return loadingMutableStateFlow.value
    }
    set(value) {
        loadingMutableStateFlow.tryEmit(value)
    }

private val <T> T.loadingMutableStateFlow: MutableStateFlow<Boolean> where T : LoadingAware, T : ViewModel
    get() {
        val flow: MutableStateFlow<Boolean>? = getTag(LOADING_FLOW_KEY)
        return flow ?: setTagIfAbsent(LOADING_FLOW_KEY, MutableStateFlow(false))
    }

private fun <T> T.getErrorMutableSharedFlow(): MutableSharedFlow<ViewError> where T : ViewErrorAware, T : ViewModel {
    val flow: MutableSharedFlow<ViewError>? = getTag(ERROR_FLOW_KEY)
    return flow ?: setTagIfAbsent(ERROR_FLOW_KEY, MutableSharedFlow())
}


fun <F, T> Flow<F>.bindLoading(t: T): Flow<F> where T : LoadingAware, T : ViewModel {
    return this
        .onStart {
            t.loadingMutableStateFlow.value = true
        }
        .onCompletion {
            t.loadingMutableStateFlow.value = false
        }
}



fun <F, T> Flow<UseCaseResult<F>>.bindError(t: T): Flow<UseCaseResult<F>> where T : ViewErrorAware, T : ViewModel {
    return this
        .onError {
            t.emitViewError(it.mapToViewError())
            errorMessage.value = ErrorMessage(
                id = UUID.randomUUID().mostSignificantBits,
                message = it.mapToViewError().message
            )
        }
}