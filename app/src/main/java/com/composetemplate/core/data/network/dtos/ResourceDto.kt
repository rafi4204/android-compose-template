package com.composetemplate.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ResourceDto(
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)