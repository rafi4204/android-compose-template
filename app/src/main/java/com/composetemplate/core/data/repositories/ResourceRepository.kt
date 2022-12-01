package com.composetemplate.core.data.repositories

import com.composetemplate.arch.data.Repository
import com.composetemplate.arch.extensions.mapSuccess
import com.composetemplate.core.data.network.Api
import com.composetemplate.core.data.network.dtos.toEntity
import com.composetemplate.core.domain.model.ResourceDetails
import com.composetemplate.core.pagination.model.Resource
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