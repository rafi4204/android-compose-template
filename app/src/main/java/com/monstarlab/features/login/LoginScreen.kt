package com.monstarlab.features.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.monstarlab.R
import com.monstarlab.arch.extensions.collectAsStateLifecycleAware
import com.monstarlab.core.ui.AppBackground
import com.monstarlab.core.ui.AppButton
import com.monstarlab.core.ui.InputTextField


@Composable
internal fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit = {}
) {
    val loginUiInfo = viewModel.loginUiInfo.collectAsStateLifecycleAware().value
    LoginScreen(
        loginUiInfo = loginUiInfo,
        onUserNameChanged = viewModel::onUserNameChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        login = viewModel::login,
        navigateToHome = navigateToHome
    )
}

@Composable
fun LoginScreen(
    loginUiInfo: LoginUiInfo,
    onUserNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    login: () -> Unit,
    navigateToHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        InputTextField(text = loginUiInfo.userName) {
            onUserNameChanged(it)
        }
        Spacer(modifier = Modifier.height(10.dp))
        InputTextField(text = loginUiInfo.password) {
            onPasswordChanged(it)
        }
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(text = stringResource(id = R.string.login)) {
            //TODO call  login()
            navigateToHome()
        }

    }

}

@Preview
@Composable
fun LoginPreview() {
    AppBackground {
        LoginScreen(LoginUiInfo("", ""), {}, {}, {}, {})
    }
}