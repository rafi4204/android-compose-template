package com.monstarlab.core.data.network.dtos

import com.monstarlab.core.domain.model.Resource

fun ResourceDto.toEntity(): Resource {
    return Resource(
        id = id,
        name = name,
        year = year,
        color = color,
        pantoneValue = pantoneValue
    )
}
