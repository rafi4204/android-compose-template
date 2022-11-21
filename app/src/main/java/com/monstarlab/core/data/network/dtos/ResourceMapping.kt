package com.monstarlab.core.data.network.dtos

import com.monstarlab.core.pagination.model.Resource

fun ResourceDto.toEntity(): Resource {
    return Resource(
        id = id,
        name = name
    )
}