package com.composetemplate.features.resources

import androidx.lifecycle.ViewModel
import com.composetemplate.arch.extensions.LoadingAware
import com.composetemplate.arch.extensions.ViewErrorAware
import com.composetemplate.core.sharedui.errorhandling.ViewError
import com.composetemplate.core.usecases.resources.GetResourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ResourceViewModel @Inject constructor(
    getResourcesUseCase: GetResourcesUseCase
) : ViewModel(), ViewErrorAware, LoadingAware {
    var resourceResult = getResourcesUseCase()

    //var resourceResult = getResourcesUseCase()
    val loadingFlow = MutableStateFlow(false)
    val errorFlow = MutableSharedFlow<ViewError>()




}