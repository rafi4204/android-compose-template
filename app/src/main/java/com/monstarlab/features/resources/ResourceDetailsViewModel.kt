package com.monstarlab.features.resources

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.monstarlab.arch.extensions.LoadingAware
import com.monstarlab.arch.extensions.ViewErrorAware
import com.monstarlab.arch.extensions.collectFlow
import com.monstarlab.core.domain.model.ResourceDetails
import com.monstarlab.core.pagination.model.Resource
import com.monstarlab.core.sharedui.errorhandling.ViewError
import com.monstarlab.core.usecases.resourceDetails.GetResourceDetailsUseCase
import com.monstarlab.core.usecases.resources.GetResourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
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