package com.monstarlab.core.data.network.responses

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val token: String
)
