package com.monstarlab.core.data.repositories

import com.monstarlab.arch.data.Repository
import com.monstarlab.arch.extensions.mapSuccess
import com.monstarlab.core.data.network.Api
import com.monstarlab.core.data.network.dtos.toEntity
import com.monstarlab.core.domain.model.ResourceDetails
import com.monstarlab.core.pagination.model.Resource
import javax.inject.Inject

class ResourceRepository @Inject constructor(
    private val api: Api,
) : Repository() {

    suspend fun getResources(page: Int, pageSize: Int): List<Resource> {
        return api.getResources(page, pageSize)
            .mapSuccess { response -> response.map { it.toEntity() } }
    }

    suspend fun getResourcesDetails(id: Int): ResourceDetails {
        return api.getResourcesDetails(id)
            .mapSuccess {
                it[0].toEntity()
            }
    }


}