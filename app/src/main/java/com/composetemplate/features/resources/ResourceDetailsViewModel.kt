package com.composetemplate.features.resources

import androidx.lifecycle.ViewModel
import com.composetemplate.arch.extensions.LoadingAware
import com.composetemplate.arch.extensions.ViewErrorAware
import com.composetemplate.arch.extensions.collectFlow
import com.composetemplate.core.domain.model.ResourceDetails
import com.composetemplate.core.usecases.resourceDetails.GetResourceDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ResourceDetailsViewModel @Inject constructor(
    private val getResourceDetailsUseCase: GetResourceDetailsUseCase
) : ViewModel(), ViewErrorAware, LoadingAware {
   val resourceDetails = MutableStateFlow(ResourceDetails(-1, "", ""))

    fun getResourceDetails(id: Int) {
        collectFlow(getResourceDetailsUseCase(id)) {
            resourceDetails.value = it
        }
    }

}