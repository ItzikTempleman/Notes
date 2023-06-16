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

@Composable
fun SetupNavGraph(navHostController: NavHostController, noteViewModel: NoteViewModel, user: User) {
    NavHost(
        navController = navHostController,
        startDestination = HOME
    ) {
        navigation(
            startDestination = AppGraph.Splash.route,
            route = AppGraph.Notes.route
        ) {
            composable(route = AppGraph.Splash.route) {
                AnimatedSplashScreen(navHostController, noteViewModel)
            }

            composable(route = AppGraph.Login.route) {
                LoginScreen(navHostController, noteViewModel)
            }

            composable(route = AppGraph.SignUp.route) {
                RegistrationScreen(navHostController, noteViewModel)
            }
            composable(route = AppGraph.Login.route) {
                LoginScreen(navHostController, noteViewModel)
            }
            composable(route = AppGraph.Forgot.route) {
                ResetPasswordScreen(navHostController, noteViewModel)
            }

            composable(route = AppGraph.Notes.route) {
                NoteListScreen(modifier = Modifier, navHostController, noteViewModel)
            }
            composable(route = AppGraph.Note.route) {
                NoteScreen(navHostController, noteViewModel, user)
            }
        }
    }
}


const val HOME = "appGraph"


sealed class AppGraph(val route: String) {

    object Splash : AppGraph(route = "splash")

    object Login : AppGraph(route = "login")
    object SignUp : AppGraph(route = "signup")
    object Forgot : AppGraph(route = "reset")

    object Notes : AppGraph(route = "noteList")
    object Note : AppGraph(route = "note")

}

