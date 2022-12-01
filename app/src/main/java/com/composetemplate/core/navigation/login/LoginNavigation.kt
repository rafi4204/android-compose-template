package com.composetemplate.core.navigation.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.composetemplate.features.login.LoginRoute

const val loginNavigationRoute = "login_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loginNavigationRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(navigateToHome: () -> Unit) {
    composable(route = loginNavigationRoute) {
        LoginRoute(navigateToHome = navigateToHome)
    }
}