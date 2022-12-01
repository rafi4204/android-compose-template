package com.composetemplate.core.usecases.user

import com.composetemplate.arch.extensions.useCaseFlow
import com.composetemplate.core.data.repositories.UserRepository
import com.composetemplate.injection.qualifiers.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    fun login(email: String, password: String) =
        useCaseFlow(coroutineDispatcher) {
            userRepository.login(email, password)
            userRepository.getUser()
        }
}