package com.monstarlab.core.data.repositories

import com.monstarlab.arch.data.Repository
import com.monstarlab.arch.extensions.mapSuccess
import com.monstarlab.core.data.network.Api
import com.monstarlab.core.data.network.dtos.toEntity
import com.monstarlab.core.data.storage.ResourcePreferenceStore
import com.monstarlab.core.domain.model.Resource
import javax.inject.Inject

class ResourceRepository @Inject constructor(
    private val api: Api,
    private val resourcePreferenceStore: ResourcePreferenceStore
) : Repository() {

    suspend fun getResources(): List<Resource> {
        return api.getResources()
            .mapSuccess { response -> response.data.map { it.toEntity() } }
            .also { resourcePreferenceStore.addAll(it) }
    }
}
