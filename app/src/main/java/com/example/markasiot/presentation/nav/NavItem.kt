package com.example.markasiot.presentation.nav

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import com.example.markasiot.R
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource

data class NavItem(
    val label: String = "",
    @DrawableRes val icon: Int,
    val route: String
)

val listNavItem: List<NavItem> = listOf(
    NavItem(
        label = "Home",
        icon = R.drawable.home,
        route = Screens.Home.route
    ),

    NavItem(
        label = "Activity",
        icon = R.drawable.graph,
        route = Screens.Activity.route
    ),

    NavItem(
        label = "Profile",
        icon = R.drawable.user,
        route = Screens.Profile.route
    )
)
