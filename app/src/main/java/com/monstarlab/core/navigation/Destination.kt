package com.monstarlab.core.navigation

import com.monstarlab.R
import com.monstarlab.core.navigation.home.homeNavigationRoute
import com.monstarlab.core.navigation.login.loginNavigationRoute
import com.monstarlab.core.navigation.resource.resourceDetailsNavigationRoute
import com.monstarlab.core.navigation.resource.resourcesNavigationRoute
import com.monstarlab.core.ui.AppIcons
import com.monstarlab.core.ui.Icon


/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */


enum class Destination(
    val isTopLevelDestination: Boolean,
    val isBottomBarTab: Boolean,
    val isTopBarTab: Boolean,
    val selectedIcon: Icon? = null,
    val unselectedIcon: Icon? = null,
    val iconTextId: Int? = null,
    val titleTextId: Int,
    val route: String
) {
    HOME(
        isTopLevelDestination = true,
        isBottomBarTab = true,
        isTopBarTab = true,
        selectedIcon = Icon.DrawableResourceIcon(AppIcons.Home),
        unselectedIcon = Icon.DrawableResourceIcon(AppIcons.HomeBorder),
        iconTextId = R.string.home,
        titleTextId = R.string.home,
        route = homeNavigationRoute
    ),
    RESOURCES(
        isTopLevelDestination = true,
        isBottomBarTab = true,
        isTopBarTab = true,
        selectedIcon = Icon.DrawableResourceIcon(AppIcons.Resources),
        unselectedIcon = Icon.DrawableResourceIcon(AppIcons.ResourcesBorder),
        iconTextId = R.string.resources,
        titleTextId = R.string.resources,
        route = resourcesNavigationRoute
    ),
    LOGIN(
        isTopLevelDestination = true,
        isBottomBarTab = false,
        isTopBarTab = false,
        titleTextId = R.string.login,
        route = loginNavigationRoute
    ),

    RESOURCE_DETAILS(
        isTopLevelDestination = false,
        isBottomBarTab = false,
        isTopBarTab = true,
        titleTextId = R.string.resources,
        route = resourceDetailsNavigationRoute
    )
}