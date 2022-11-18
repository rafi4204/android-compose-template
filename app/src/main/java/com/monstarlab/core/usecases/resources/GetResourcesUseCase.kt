package com.monstarlab.core.usecases.resources

import com.monstarlab.arch.extensions.useCaseFlow
import com.monstarlab.core.data.repositories.ResourceRepository
import com.monstarlab.injection.qualifiers.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetResourcesUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    fun getResources() = useCaseFlow(coroutineDispatcher) {
        delay(2000)
        resourceRepository.getResources()
    }
}
