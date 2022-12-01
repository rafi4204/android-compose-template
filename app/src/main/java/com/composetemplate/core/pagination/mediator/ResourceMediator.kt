package com.composetemplate.core.pagination.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.composetemplate.core.data.repositories.ResourceRepository
import com.composetemplate.core.pagination.model.RemoteKeys
import com.composetemplate.core.pagination.model.Resource
import com.composetemplate.core.pagination.db.AppDatabase
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class ResourceMediator(
    private val resourceRepository: ResourceRepository,
    private val appDatabase: AppDatabase
) :
    RemoteMediator<Int, Resource>() {
    companion object {
        const val DEFAULT_PAGE_INDEX = 2
        const val DEFAULT_PAGE_SIZE = 10
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, Resource>
    ): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = resourceRepository.getResources(page, state.config.pageSize)
            val isEndOfList = response.isEmpty()
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.getRepoDao().clearRemoteKeys()
                    appDatabase.getResourceDao().clearAllResources()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.map {
                    RemoteKeys(repoId = it.id.toString(), prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.getRepoDao().insertAll(keys)
                appDatabase.getResourceDao().insertAll(response)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Resource>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                if (remoteKeys == null)
                    DEFAULT_PAGE_INDEX
                else if (remoteKeys.nextKey == null)
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                else remoteKeys.nextKey
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, Resource>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { resource ->
                appDatabase.getRepoDao().remoteKeysResourceId(resource.id.toString())
            }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, Resource>): RemoteKeys? {
        return state.pages
            .firstOrNull() {
                it.data.isNotEmpty()
            }
            ?.data?.firstOrNull()
            ?.let { doggo -> appDatabase.getRepoDao().remoteKeysResourceId(doggo.id.toString()) }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, Resource>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                appDatabase.getRepoDao().remoteKeysResourceId(repoId.toString())
            }
        }
    }

}