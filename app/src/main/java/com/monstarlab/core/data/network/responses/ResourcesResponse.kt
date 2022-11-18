package com.monstarlab.core.data.network.responses

import com.monstarlab.core.data.network.dtos.ResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResourcesResponse(
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    val data: List<ResourceDto>
)
