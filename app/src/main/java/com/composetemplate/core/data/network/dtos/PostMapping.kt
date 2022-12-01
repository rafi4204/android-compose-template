package com.composetemplate.core.data.network.dtos

import com.composetemplate.core.domain.model.Post

fun PostDto.toEntity(): Post {
    return Post(
        id = id,
        title = title,
        body = body
    )
}