package com.composetemplate.core.data.network.dtos

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResourceDetailsDto(
    val id: Int,
    val name: String,
    @SerializedName("sprites")
    val sprites: SpritesDto
)

@Serializable
data class SpritesDto(
    @SerializedName("back_default")
    val imageUrl: String?,
)