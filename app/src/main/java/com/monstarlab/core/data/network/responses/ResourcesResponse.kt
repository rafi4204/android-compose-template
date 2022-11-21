package com.monstarlab.core.data.network.responses

import com.monstarlab.core.data.network.dtos.ResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResourcesResponse(
    val data: List<ResourceDto>
)