package com.monstarlab.core.data.network.dtos

import com.monstarlab.core.domain.model.ResourceDetails

fun ResourceDetailsDto.toEntity(): ResourceDetails {
    return ResourceDetails(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}