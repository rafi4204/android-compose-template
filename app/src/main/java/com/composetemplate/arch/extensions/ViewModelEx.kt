@file:Suppress("PackageDirectoryMismatch")

package androidx.lifecycle

import com.composetemplate.arch.extensions.*
import com.composetemplate.core.domain.error.ErrorMessage
import com.composetemplate.core.sharedui.errorhandling.ViewError
import com.composetemplate.core.sharedui.errorhandling.mapToViewError
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.ConcurrentHashMap

private const val ERROR_FLOW_KEY = "androidx.lifecycle.ErrorFlow"
private const val LOADING_FLOW_KEY = "androidx.lifecycle.LoadingFlow"
val errorMessage by lazy { MutableStateFlow(ErrorMessage(-1, "")) }

// Create a ConcurrentHashMap to store the tags
private val viewModelTags = ConcurrentHashMap<String, Any>()

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
        // Use the ConcurrentHashMap to store and retrieve the flow
        return viewModelTags.getOrPut(LOADING_FLOW_KEY) { MutableStateFlow(false) } as MutableStateFlow<Boolean>
    }

private fun <T> T.getErrorMutableSharedFlow(): MutableSharedFlow<ViewError> where T : ViewErrorAware, T : ViewModel {
    // Use the ConcurrentHashMap to store and retrieve the flow
    return viewModelTags.getOrPut(ERROR_FLOW_KEY) { MutableSharedFlow<ViewError>() } as MutableSharedFlow<ViewError>
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