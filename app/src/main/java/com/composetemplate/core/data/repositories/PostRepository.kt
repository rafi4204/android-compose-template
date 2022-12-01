package com.composetemplate.core.data.repositories

import com.composetemplate.arch.data.Repository
import com.composetemplate.arch.extensions.mapSuccess
import com.composetemplate.core.data.network.Api
import com.composetemplate.core.data.network.dtos.toEntity
import com.composetemplate.core.data.storage.PostPreferenceStore
import com.composetemplate.core.domain.model.Post
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val api: Api,
    private val postPreferenceStore: PostPreferenceStore
) : Repository() {

    suspend fun getPosts(): List<Post> {
        return api.getPosts()
            .mapSuccess { list -> list.map { it.toEntity() } }
            .also { postPreferenceStore.addAll(it) }
    }
}