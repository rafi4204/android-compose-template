package com.composetemplate.core.data.repositories

import com.composetemplate.arch.data.Repository
import com.composetemplate.arch.extensions.mapSuccess
import com.composetemplate.arch.extensions.repoCall
import com.composetemplate.core.data.network.Api
import com.composetemplate.core.data.network.dtos.toUser
import com.composetemplate.core.data.network.responses.TokenResponse
import com.composetemplate.core.data.storage.UserPreferenceStore
import com.composetemplate.core.domain.model.User
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