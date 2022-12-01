package com.composetemplate.core.usecases.blog

import com.composetemplate.arch.extensions.useCaseFlow
import com.composetemplate.core.data.repositories.PostRepository
import com.composetemplate.injection.qualifiers.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    fun getPosts() = useCaseFlow(coroutineDispatcher) {
        postRepository.getPosts()
    }
}