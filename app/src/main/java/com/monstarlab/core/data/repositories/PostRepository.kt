package com.monstarlab.core.data.repositories

import com.monstarlab.arch.data.Repository
import com.monstarlab.arch.extensions.mapSuccess
import com.monstarlab.core.data.network.Api
import com.monstarlab.core.data.network.dtos.toEntity
import com.monstarlab.core.data.storage.PostPreferenceStore
import com.monstarlab.core.domain.model.Post
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
