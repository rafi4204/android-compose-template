package com.monstarlab.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.monstarlab.features.resources.ResourceDetailsRoute
import org.jetbrains.annotations.VisibleForTesting

@VisibleForTesting
internal const val resourceIdArg = "resourceId"
internal const val resourceDetailsRoute = "resource_details_route"
internal const val resourceDetailsNavigationRoute = "$resourceDetailsRoute/{$resourceIdArg}"

fun NavController.navigateToResourceDetails(resourceId: Int) {
    this.navigate("$resourceDetailsRoute/$resourceId")
}

fun NavGraphBuilder.resourceDetailsScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = resourceDetailsNavigationRoute,
        arguments = listOf(
            navArgument(resourceIdArg) { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val resourceId = arguments.getString(resourceIdArg)
        ResourceDetailsRoute(resourceId = resourceId, onBackClick = onBackClick)
    }
}