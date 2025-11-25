package com.example.widgetlearn.navigation

sealed class NavRoutes(val route: String) {
    data object Home : NavRoutes("home")
}
