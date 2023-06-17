package com.itzik.notes.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itzik.notes.project.models.user.User
import com.itzik.notes.project.screens.login_screens.LoginScreen

import com.itzik.notes.project.screens.login_screens.RegistrationScreen
import com.itzik.notes.project.screens.login_screens.ResetPasswordScreen

import com.itzik.notes.project.screens.note_screens.NoteListScreen
import com.itzik.notes.project.screens.note_screens.NoteScreen
import com.itzik.notes.project.screens.splash_screen.AnimatedSplashScreen
import com.itzik.notes.project.viewmodels.NoteViewModel

const val SPLASH_ROOT = "rootGraph"
const val AUTH = "authGraph"
const val HOME = "homeGraph"

@Composable
fun SetupNavGraph(navHostController: NavHostController, noteViewModel: NoteViewModel, user: User) {
    NavHost(
        navController = navHostController,
        startDestination = SPLASH_ROOT
    ) {
        navigation(
            startDestination = RootSplashGraph.Splash.route,
            route = SPLASH_ROOT
        ) {
            composable(route = RootSplashGraph.Splash.route) {
                AnimatedSplashScreen(navHostController, noteViewModel)
            }
        }
        navigation(
            startDestination = AuthGraph.Login.route,
            route = AUTH
        ) {
            composable(route = AuthGraph.Login.route) {
                LoginScreen(navHostController, noteViewModel)
            }
            composable(route = AuthGraph.SignUp.route) {
                RegistrationScreen(navHostController, noteViewModel)
            }
            composable(route = AuthGraph.Forgot.route) {
                ResetPasswordScreen(navHostController, noteViewModel)
            }
        }
        navigation(
            startDestination = HomeGraph.Notes.route,
            route = HOME
        ) {
            composable(route = HomeGraph.Notes.route) {
                NoteListScreen(modifier = Modifier, navHostController, noteViewModel, user)
            }
            composable(route = HomeGraph.Note.route) {
                NoteScreen(navHostController, noteViewModel, user)
            }
        }
    }
}
sealed class RootSplashGraph(val route: String) {
    object Splash : RootSplashGraph(route = "splash")
}
sealed class AuthGraph(val route: String) {
    object Login : AuthGraph(route = "login")
    object SignUp : AuthGraph(route = "signup")
    object Forgot : AuthGraph(route = "reset")
}
sealed class HomeGraph(val route: String) {
    object Notes : HomeGraph(route = "noteList")
    object Note : HomeGraph(route = "note")
}

