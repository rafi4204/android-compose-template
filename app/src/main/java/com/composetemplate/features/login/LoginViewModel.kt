package com.composetemplate.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.bindError
import androidx.lifecycle.bindLoading
import androidx.lifecycle.viewModelScope
import com.composetemplate.arch.extensions.LoadingAware
import com.composetemplate.arch.extensions.ViewErrorAware
import com.composetemplate.arch.extensions.onSuccess
import com.composetemplate.core.usecases.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel(), ViewErrorAware, LoadingAware {
    val loginUiInfo by lazy {
        MutableStateFlow(
            LoginUiInfo("", "")
        )
    }
    val loginResultFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()

    fun login() {
        loginUseCase
            .login(loginUiInfo.value.userName, loginUiInfo.value.password)
            .bindLoading(this)
            .bindError(this)
            .onSuccess {
                loginResultFlow.emit(true)
            }
            .launchIn(viewModelScope)
    }

    fun onUserNameChanged(userName: String) {
        loginUiInfo.value = loginUiInfo.value.copy(userName = userName)
    }

    fun onPasswordChanged(password: String) {
        loginUiInfo.value = loginUiInfo.value.copy(password = password)
    }
}

data class LoginUiInfo(
    val userName: String,
    val password: String
)