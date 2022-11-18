package com.monstarlab.core.usecases.user

import com.monstarlab.arch.extensions.useCaseFlow
import com.monstarlab.core.data.repositories.UserRepository
import com.monstarlab.injection.qualifiers.DefaultDispatcher
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
