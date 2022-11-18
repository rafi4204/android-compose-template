package com.monstarlab.core.data.network.responses

import com.monstarlab.core.data.network.dtos.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val data: UserDto
)
