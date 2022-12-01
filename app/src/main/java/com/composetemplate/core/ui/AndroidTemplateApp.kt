package com.composetemplate.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.zIndex
import androidx.lifecycle.errorMessage
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.composetemplate.arch.extensions.collectAsStateLifecycleAware
import com.composetemplate.core.navigation.AppNavHost
import com.composetemplate.core.navigation.AppNavigationBar
import com.composetemplate.core.navigation.AppNavigationBarItem
import com.composetemplate.core.navigation.Destination

@OptIn(
    ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AndroidTemplateApp(
    appState: AppState
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier
                    .systemBarsPadding()
                    .navigationBarsPadding()
            )
        },

        topBar = {
            val destination = appState.currentDestination
            if (appState.shouldShowTopAppBar) {
                AppTopAppBar(
                    modifier = Modifier.zIndex(-1F),
                    titleRes = destination?.titleTextId ?: -1,
                    actionIcon = AppIcons.Settings,
                    actionIconContentDescription = stringResource(
                        id = com.composetemplate.R.string.icon
                    ),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    onActionClick = { },
                    navigationIcon = Icons.Default.ArrowBack,
                    navigationIconContentDescription = "",
                    onNavigationClick = { appState.onBackClick() }

                )
            }
        },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                AppBottomBar(
                    destinations = appState.destinationWithBottomBars,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestinationAsState
                )
            }
        }
    ) { padding ->
        val message = errorMessage.collectAsStateLifecycleAware().value
        LaunchedEffect(key1 = message.id) {
            if (message.message.isNotEmpty())
                snackbarHostState.showSnackbar(message = message.message)
        }
        AppNavHost(
            navController = appState.navController,
            onBackClick = appState::onBackClick,
            modifier = Modifier
                .padding(padding)
                .consumedWindowInsets(padding)
                .systemBarsPadding()
                .statusBarsPadding()
                .navigationBarsPadding()
        )
    }
}

@Composable
private fun AppBottomBar(
    destinations: List<Destination>,
    onNavigateToDestination: (Destination) -> Unit,
    currentDestination: NavDestination?
) {
    AppNavigationBar {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            AppNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    when (icon) {
                        is Icon.ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null
                        )

                        is Icon.DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null
                        )
                    }
                },
                label = { Text(stringResource(destination.iconTextId ?: -1)) }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: Destination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false