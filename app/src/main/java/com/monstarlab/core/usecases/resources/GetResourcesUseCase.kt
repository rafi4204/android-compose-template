package com.monstarlab.core.usecases.resources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.monstarlab.arch.extensions.useCaseFlow
import com.monstarlab.core.data.repositories.ResourceRepository
import com.monstarlab.core.pagination.db.AppDatabase
import com.monstarlab.core.pagination.mediator.ResourceMediator
import com.monstarlab.core.pagination.mediator.ResourceMediator.Companion.DEFAULT_PAGE_SIZE
import com.monstarlab.injection.qualifiers.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GetResourcesUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
    private val appDatabase: AppDatabase,
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    operator fun invoke() = useCaseFlow(coroutineDispatcher) {
        val pagingSourceFactory = { appDatabase.getImageModelDao().getAllResources() }
        Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = ResourceMediator(resourceRepository, appDatabase)
        ).flow
    }
}