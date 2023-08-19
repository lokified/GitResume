package com.loki.gitresume.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.loki.gitresume.AppState
import com.loki.gitresume.ui.forgot_password.ForgotPasswordScreen
import com.loki.gitresume.ui.forgot_password.ForgotPasswordViewModel
import com.loki.gitresume.ui.login.LoginScreen
import com.loki.gitresume.ui.login.LoginViewModel
import com.loki.gitresume.ui.register.RegisterScreen
import com.loki.gitresume.ui.register.RegisterViewModel

@Composable
fun Navigation(appState: AppState) {

    NavHost(
        navController = appState.navController,
        startDestination = Screen.LoginScreen.route
    ) {

        composable(route = Screen.LoginScreen.route) {
            val viewModel = LoginViewModel()
            LoginScreen(
                viewModel = viewModel,
                openSignUpScreen = { route -> appState.navigate(route) },
                openHomeScreen = { route, pop -> appState.navigateAndPopUp(route, pop)}
            )
        }

        composable(route = Screen.RegisterScreen.route) {
            val viewModel = RegisterViewModel()
            RegisterScreen(
                viewModel = viewModel,
                openLoginScreen = {
                    appState.navigate(Screen.LoginScreen.route)
                }
            )
        }

        composable(route = Screen.ForgotPasswordScreen.route) {
            val viewModel = ForgotPasswordViewModel()
            ForgotPasswordScreen(
                viewModel = viewModel,
                openLoginScreen = { appState.navigate(Screen.LoginScreen.route) },
                popScreen = { appState.popUp() }
            )
        }

        composable(route = Screen.HomeScreen.route) {

        }

        composable(route = Screen.RepositoryScreen.route) {

        }
    }
}

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object ForgotPasswordScreen: Screen("forgot_screen")
    object HomeScreen: Screen("home_screen")
    object RepositoryScreen: Screen("repository_screen")
}