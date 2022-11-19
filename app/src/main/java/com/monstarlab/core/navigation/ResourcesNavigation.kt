package com.monstarlab.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation

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
            /*ResourcesRoute(
                navigateToTopic = navigateToTopic,
                navigateToAuthor = navigateToAuthor,
            )*/
        }
        nestedGraphs()
    }
}