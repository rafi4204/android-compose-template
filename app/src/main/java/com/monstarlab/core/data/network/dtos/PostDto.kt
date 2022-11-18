package com.monstarlab.core.data.network.dtos

data class PostDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
