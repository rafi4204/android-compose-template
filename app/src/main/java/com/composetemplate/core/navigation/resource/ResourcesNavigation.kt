package com.composetemplate.core.navigation.resource

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.composetemplate.features.resources.ResourceRoute

const val resourcesGraphRoutePattern = "resources_graph"
const val resourcesNavigationRoute = "resources_route"

fun NavController.navigateToResourcesGraph(navOptions: NavOptions? = null) {
    this.navigate(resourcesGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.resourcesGraph(
    onItemClick: (Int) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
    navController: NavController
) {
    navigation(
        route = resourcesGraphRoutePattern,
        startDestination = resourcesNavigationRoute
    ) {
        composable(route = resourcesNavigationRoute) {
            ResourceRoute(navController = navController, onItemClick = onItemClick)
        }
        nestedGraphs()
    }
}