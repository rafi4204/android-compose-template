package com.monstarlab.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.monstarlab.features.resources.ResourceRoute

private const val resourcesGraphRoutePattern = "resources_graph"
const val resourcesNavigationRoute = "resources_route"

fun NavController.navigateToResourcesGraph(navOptions: NavOptions? = null) {
    this.navigate(resourcesGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.resourcesGraph(
    navigateToDetails: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = resourcesGraphRoutePattern,
        startDestination = resourcesNavigationRoute
    ) {
        composable(route = resourcesNavigationRoute) {
            ResourceRoute(navigateToDetails = navigateToDetails)
        }
        nestedGraphs()
    }
}