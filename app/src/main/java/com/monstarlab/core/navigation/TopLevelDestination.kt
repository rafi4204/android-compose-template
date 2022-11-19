package com.monstarlab.core.navigation

import com.monstarlab.R
import com.monstarlab.core.ui.AppIcons
import com.monstarlab.core.ui.Icon


/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */


enum class TopLevelDestination(
    val selectedIcon: Icon? = null,
    val unselectedIcon: Icon? = null,
    val iconTextId: Int? = null,
    val titleTextId: Int,
    val route: String
) {
    HOME(
        selectedIcon = Icon.DrawableResourceIcon(AppIcons.Home),
        unselectedIcon = Icon.DrawableResourceIcon(AppIcons.HomeBorder),
        iconTextId = R.string.home,
        titleTextId = R.string.home,
        route = homeNavigationRoute
    ),
    RESOURCES(
        selectedIcon = Icon.DrawableResourceIcon(AppIcons.Resources),
        unselectedIcon = Icon.DrawableResourceIcon(AppIcons.ResourcesBorder),
        iconTextId = R.string.resources,
        titleTextId = R.string.resources,
        route = resourcesNavigationRoute
    ),
    LOGIN(
        titleTextId = R.string.login,
        route = loginNavigationRoute
    )

}