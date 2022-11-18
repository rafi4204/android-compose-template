package com.monstarlab.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
)
