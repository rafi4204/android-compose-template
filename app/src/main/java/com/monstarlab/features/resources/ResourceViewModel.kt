package com.monstarlab.features.resources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstarlab.arch.extensions.onError
import com.monstarlab.arch.extensions.onSuccess
import com.monstarlab.core.domain.model.Resource
import com.monstarlab.core.sharedui.errorhandling.ViewError
import com.monstarlab.core.sharedui.errorhandling.mapToViewError
import com.monstarlab.core.usecases.resources.GetResourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ResourceViewModel @Inject constructor(
    private val getResourcesUseCase: GetResourcesUseCase
) : ViewModel() {

    val loadingFlow = MutableStateFlow(false)
    val errorFlow = MutableSharedFlow<ViewError>()
    val resourcesFlow: MutableStateFlow<List<Resource>> = MutableStateFlow(emptyList())

    fun fetchResources() {
        getResourcesUseCase
            .getResources()
            .onStart {
                loadingFlow.emit(true)
            }.onSuccess {
                resourcesFlow.value = it
            }.onError {
                errorFlow.emit(it.mapToViewError())
            }.onCompletion {
                loadingFlow.emit(false)
            }.launchIn(viewModelScope)
    }
}
