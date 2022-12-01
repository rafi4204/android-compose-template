package com.composetemplate.core.usecases.resources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.composetemplate.core.data.repositories.ResourceRepository
import com.composetemplate.core.pagination.db.AppDatabase
import com.composetemplate.core.pagination.mediator.ResourceMediator
import com.composetemplate.core.pagination.mediator.ResourceMediator.Companion.DEFAULT_PAGE_SIZE
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GetResourcesUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
    private val appDatabase: AppDatabase,
) {
    operator fun invoke() =
        Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            remoteMediator = ResourceMediator(resourceRepository, appDatabase)
        ) {
            appDatabase.getResourceDao().getAllResources()
        }.flow

}