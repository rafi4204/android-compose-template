package com.monstarlab.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResourceDto(
    val id: Int,
    val name: String,
    val year: Int,
    val color: String,
    @SerialName("pantone_value")
    val pantoneValue: String
)
