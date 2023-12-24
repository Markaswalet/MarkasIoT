package com.example.markasiot.presentation.nav

sealed class Screens(val route: String){
    data object Home : Screens("home_route")
    data object Activity : Screens("activity_route")
    data object Profile : Screens("profile_route")
}
