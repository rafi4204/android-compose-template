package com.monstarlab.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResourceDetailsDto(
    val id: Int,
    val name: String,
    val tagline: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("first_brewed")
    val firstBrewed: String
)