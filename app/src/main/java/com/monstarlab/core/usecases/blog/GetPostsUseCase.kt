package com.monstarlab.core.usecases.blog

import com.monstarlab.arch.extensions.useCaseFlow
import com.monstarlab.core.data.repositories.PostRepository
import com.monstarlab.injection.qualifiers.DefaultDispatcher
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
