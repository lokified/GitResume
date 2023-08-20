package com.loki.gitresume.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.loki.gitresume.AppState
import com.loki.gitresume.ui.forgot_password.ForgotPasswordScreen
import com.loki.gitresume.ui.forgot_password.ForgotPasswordViewModel
import com.loki.gitresume.ui.home.HomeScreen
import com.loki.gitresume.ui.home.HomeViewModel
import com.loki.gitresume.ui.login.LoginScreen
import com.loki.gitresume.ui.login.LoginViewModel
import com.loki.gitresume.ui.projects.ProjectsScreen
import com.loki.gitresume.ui.register.RegisterScreen
import com.loki.gitresume.ui.register.RegisterViewModel
import com.loki.gitresume.ui.repository.RepositoryScreen
import com.loki.gitresume.ui.repository.RepositoryViewModel
import kotlinx.coroutines.delay

@Composable
fun Navigation(appState: AppState) {

    NavHost(
        navController = appState.navController,
        startDestination = Screen.LoginScreen.route
    ) {

        composable(route = Screen.LoginScreen.route) {
            val viewModel = hiltViewModel<LoginViewModel>()

            LaunchedEffect(key1 = Unit) {
                viewModel.onAppStart {
                    appState.navigateAndPopUp(Screen.HomeScreen.route, Screen.LoginScreen.route)
                }
                delay(3000L)
                viewModel.isLoggingIn.value = false
            }

            if (!viewModel.isLoggingIn.value) {

                LoginScreen(
                    viewModel = viewModel,
                    openSignUpScreen = { route: String -> appState.navigate(route) },
                    openHomeScreen = { route: String, pop: String ->
                        appState.navigateAndPopUp(
                            route,
                            pop
                        )
                    },
                    openForgotScreen = { route -> appState.navigate(route) }
                )
            }
        }

        composable(route = Screen.RegisterScreen.route) {
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(
                viewModel = viewModel,
                openLoginScreen = {
                    appState.navigate(Screen.LoginScreen.route)
                }
            )
        }

        composable(route = Screen.ForgotPasswordScreen.route) {
            val viewModel = hiltViewModel<ForgotPasswordViewModel>()
            ForgotPasswordScreen(
                viewModel = viewModel,
                openLoginScreen = { appState.navigateAndPopUp(Screen.LoginScreen.route, Screen.ForgotPasswordScreen.route) },
                popScreen = { appState.popUp() }
            )
        }

        composable(route = Screen.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
                openRepositoryScreen = { appState.navigate(Screen.RepositoryScreen.route) },
                openProjectScreen = { appState.navigate(Screen.ProjectsScreen.route) },
                openLoginScreen = { appState.navigateAndPopUp(Screen.LoginScreen.route, Screen.HomeScreen.route) }
            )
        }

        composable(route = Screen.RepositoryScreen.route) {
            val viewModel = hiltViewModel<RepositoryViewModel>()
            RepositoryScreen(viewModel = viewModel)
        }

        composable(route = Screen.ProjectsScreen.route) {

            ProjectsScreen(
                openRepositoryScreen = { appState.navigate(Screen.RepositoryScreen.route) }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object ForgotPasswordScreen: Screen("forgot_screen")
    object HomeScreen: Screen("home_screen")
    object RepositoryScreen: Screen("repository_screen")
    object ProjectsScreen: Screen("project_screen")
}