package com.composetemplate.core.usecases.resourceDetails

import com.composetemplate.arch.extensions.useCaseFlow
import com.composetemplate.core.data.repositories.ResourceRepository
import com.composetemplate.injection.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetResourceDetailsUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    operator fun invoke(id: Int) = useCaseFlow(coroutineDispatcher = coroutineDispatcher) {
        resourceRepository.getResourcesDetails(id)
    }


}