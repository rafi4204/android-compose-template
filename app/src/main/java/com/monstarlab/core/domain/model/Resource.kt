package com.monstarlab.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Resource(
    val id: Int,
    val name: String,
    val year: Int,
    val color: String,
    val pantoneValue: String
)
