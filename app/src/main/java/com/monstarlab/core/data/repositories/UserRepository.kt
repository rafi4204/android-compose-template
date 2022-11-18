package com.monstarlab.core.data.repositories

import com.monstarlab.arch.data.Repository
import com.monstarlab.arch.extensions.mapSuccess
import com.monstarlab.arch.extensions.repoCall
import com.monstarlab.core.data.network.Api
import com.monstarlab.core.data.network.dtos.toUser
import com.monstarlab.core.data.network.responses.TokenResponse
import com.monstarlab.core.data.storage.UserPreferenceStore
import com.monstarlab.core.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: Api,
    private val userPreferenceStore: UserPreferenceStore
) : Repository() {

    suspend fun login(email: String, password: String): TokenResponse = repoCall {
        api.postLogin(email, password)
    }

    suspend fun getUser(): User {
        return api.getUser()
            .mapSuccess {
                it.data.toUser()
            }.also {
                userPreferenceStore.add(it)
            }
    }
}
