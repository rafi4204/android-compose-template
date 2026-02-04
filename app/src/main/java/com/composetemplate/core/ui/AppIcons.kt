package com.composetemplate.core.ui

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.composetemplate.R

/**
 * App icons. Material icons are [ImageVector]s, custom icons are drawable resource IDs.
 */
object AppIcons {
    val AccountCircle = Icons.Outlined.AccountCircle
    val Add = Icons.Rounded.Add
    val ArrowBack = Icons.Rounded.ArrowBack
    val ArrowDropDown = Icons.Rounded.ArrowDropDown
    val Home = R.drawable.ic_home
    val HomeBorder = R.drawable.ic_home_border
    val Resources = R.drawable.ic_resources
    val ResourcesBorder = R.drawable.ic_resources
    val Check = Icons.Rounded.Check
    val Close = Icons.Rounded.Close
    val MoreVert = Icons.Default.MoreVert
    val Person = Icons.Rounded.Person
    val PlayArrow = Icons.Rounded.PlayArrow
    val Search = Icons.Rounded.Search
    val Settings = Icons.Rounded.Settings
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}