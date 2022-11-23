package com.monstarlab.core.ui

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.monstarlab.core.navigation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState {
    return remember(
        navController,
        windowSizeClass,
        coroutineScope
    ) {
        AppState(
            navController = navController,
            windowSizeClass = windowSizeClass,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
    val coroutineScope: CoroutineScope
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeNavigationRoute -> TopLevelDestination.HOME
            resourcesNavigationRoute -> TopLevelDestination.RESOURCES
            loginNavigationRoute -> TopLevelDestination.LOGIN
            else -> null
        }
    val shouldShowBottomBar: Boolean
        @Composable get() = BottomBarTab.values().map { it.route }
            .contains(currentDestination?.route)

    val shouldShowTopAppBar: Boolean
        @Composable get() = TopAppBarScreen.values().map { it.route }
            .contains(currentDestination?.route)

    val topLevelDestinationWithBottomBars: List<TopLevelDestination>
        get() = TopLevelDestination.values().asList()
            .filter { BottomBarTab.values().map { it.route }.contains(it.route) }


    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            TopLevelDestination.RESOURCES -> navController.navigateToResourcesGraph(
                topLevelNavOptions
            )
            TopLevelDestination.LOGIN -> navController.navigateToLogin(topLevelNavOptions)
        }

    }

    fun onBackClick() {
        navController.popBackStack()
    }

}

@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}