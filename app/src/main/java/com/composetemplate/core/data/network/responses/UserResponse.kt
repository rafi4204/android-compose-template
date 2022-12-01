package com.composetemplate.core.data.network.responses

import com.composetemplate.core.data.network.dtos.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val data: UserDto
)