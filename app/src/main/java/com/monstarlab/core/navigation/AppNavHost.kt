package com.monstarlab.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.monstarlab.core.navigation.home.homeScreen
import com.monstarlab.core.navigation.home.navigateToHome
import com.monstarlab.core.navigation.login.loginNavigationRoute
import com.monstarlab.core.navigation.login.loginScreen
import com.monstarlab.core.navigation.resource.navigateToResourceDetails
import com.monstarlab.core.navigation.resource.resourceDetailsScreen
import com.monstarlab.core.navigation.resource.resourcesGraph


@Composable
fun AppNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = loginNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen()
        loginScreen(navigateToHome = { navController.navigateToHome() })
        resourcesGraph(
            navController = navController,
            onItemClick = { navController.navigateToResourceDetails(it) },
            nestedGraphs = {
                resourceDetailsScreen(navController,onBackClick)
            }
        )
    }
}