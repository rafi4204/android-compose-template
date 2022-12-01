package com.composetemplate.core.data.network.dtos

import com.composetemplate.core.pagination.model.Resource

fun ResourceDto.toEntity(): Resource {
    return Resource(
        id = id,
        name = name
    )
}