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
import kotlinx.coroutines.launch


@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState {
    return remember(
        scaffoldState,
        navController,
        snackbarManager,
        resources,
        coroutineScope,
        windowSizeClass
    ) {
        AppState(
            scaffoldState,
            navController,
            snackbarManager = snackbarManager,
            resources = resources,
            coroutineScope = coroutineScope,
            windowSizeClass = windowSizeClass
        )
    }
}

@Stable
class AppState(
    private val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    private val snackbarManager: SnackbarManager,
    private val resources: Resources,
) {
    init {
        coroutineScope.launch {
            snackbarManager.messages.collect { currentMessages ->
                if (currentMessages.isNotEmpty()) {
                    val message = currentMessages[0]

                    // Display the snackbar on the screen. `showSnackbar` is a function
                    // that suspends until the snackbar disappears from the screen
                    scaffoldState.snackbarHostState.showSnackbar(message.toString())
                    // Once the snackbar is gone or dismissed, notify the SnackbarManager
                    snackbarManager.setMessageShown(message.id)
                }
            }
        }
    }

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