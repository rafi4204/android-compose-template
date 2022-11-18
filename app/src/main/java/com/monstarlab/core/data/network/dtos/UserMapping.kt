package com.monstarlab.core.data.network.dtos

import com.monstarlab.core.domain.model.User

fun UserDto.toUser(): User {
    return User(
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}
