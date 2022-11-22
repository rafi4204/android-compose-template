package com.monstarlab.core.usecases.resourceDetails

import com.monstarlab.arch.extensions.useCaseFlow
import com.monstarlab.core.data.repositories.ResourceRepository
import com.monstarlab.core.pagination.db.AppDatabase
import com.monstarlab.injection.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetResourceDetailsUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
    private val appDatabase: AppDatabase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = useCaseFlow(coroutineDispatcher = coroutineDispatcher) {

    }


}