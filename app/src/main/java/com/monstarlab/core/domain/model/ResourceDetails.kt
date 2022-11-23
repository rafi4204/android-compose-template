package com.monstarlab.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ResourceDetails(
    val id: Int,
    val name: String,
    val imageUrl: String
)