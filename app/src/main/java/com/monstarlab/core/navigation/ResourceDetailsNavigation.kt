package com.monstarlab.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.jetbrains.annotations.VisibleForTesting

@VisibleForTesting
internal const val resourceIdArg = "resourceId"
internal const val resourceRoute = "resource_route"

fun NavController.navigateToTopic(resourceId: String) {
    this.navigate("$resourceRoute/$resourceId")
}

fun NavGraphBuilder.resourceDetailsScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "$resourceRoute/{$resourceIdArg}",
        arguments = listOf(
            navArgument(resourceIdArg) { type = NavType.StringType }
        )
    ) {
        //TopicRoute(onBackClick = onBackClick)
    }
}