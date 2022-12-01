package com.composetemplate.core.data.network.dtos

import com.composetemplate.core.domain.model.ResourceDetails

fun ResourceDetailsDto.toEntity(): ResourceDetails {
    return ResourceDetails(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}