package com.composetemplate.core.data.network.responses

import com.composetemplate.core.data.network.dtos.ResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResourcesResponse(
    @SerialName("results")
    val results: List<ResourceDto>
)