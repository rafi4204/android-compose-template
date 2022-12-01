package com.composetemplate.core.navigation.resource

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.composetemplate.core.domain.model.ResourceDetails
import com.composetemplate.features.resources.ResourceDetailsRoute
import org.jetbrains.annotations.VisibleForTesting
import timber.log.Timber

@VisibleForTesting
internal const val resourceIdArg = "resourceId"
internal const val resourceDetailsArg = "resourceDetailsArg"
internal const val resourceDetailsRoute = "resource_details_route"
internal const val resourceDetailsNavigationRoute = "$resourceDetailsRoute/{$resourceIdArg}"

fun NavController.navigateToResourceDetails(resourceId: Int) {
    this.navigate("$resourceDetailsRoute/$resourceId")
}

fun NavGraphBuilder.resourceDetailsScreen(
    navController: NavHostController,
    onBackClick: () -> Unit
) {
    composable(
        route = resourceDetailsNavigationRoute,
        arguments = listOf(
            navArgument(resourceIdArg) { type = NavType.StringType },
        )
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val resourceId = arguments.getString(resourceIdArg)
        val resourceDetails =
            navController.previousBackStackEntry?.savedStateHandle?.get<ResourceDetails>("resourceDetails")
        Timber.tag("resource!!").d(resourceDetails?.name)
        ResourceDetailsRoute(resourceId = resourceId, onBackClick = onBackClick)
    }
}