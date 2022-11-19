package com.monstarlab.core.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.monstarlab.core.navigation.*
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
): AppState {
    return remember(navController, coroutineScope, windowSizeClass) {
        AppState(navController, coroutineScope, windowSizeClass)
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass
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