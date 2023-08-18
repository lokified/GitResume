package com.loki.gitresume

import androidx.navigation.NavHostController

class AppState (
    val navController: NavHostController
) {

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route = route) {
            launchSingleTop = true
        }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) {
                inclusive = true
            }
        }
    }
}