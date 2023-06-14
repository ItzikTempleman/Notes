package com.itzik.notes.project.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itzik.notes.project.models.note.Note
import com.itzik.notes.project.models.user.User
import com.itzik.notes.project.navigation.AppGraph.AUTH
import com.itzik.notes.project.navigation.AppGraph.NOTES_HOME
import com.itzik.notes.project.screens.splash_screen.AnimatedSplashScreen
import com.itzik.notes.project.viewmodels.NoteViewModel

@Composable
fun SetupNavGraph(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    NavHost(
        navController = navHostController,
        startDestination = NoteHomeScreen.Splash.route
    ) {
        composable(route = NoteHomeScreen.Splash.route) {
            AnimatedSplashScreen(navHostController)
        }

        authNavGraph(navHostController = navHostController)

//        composable(route = Screen.Home.route) {
//                NoteHomeScreen(noteViewModel =noteViewModel , navHostController = navHostController, modifier =Modifier.fillMaxSize() )
//        }

        navigation(
            route = NOTES_HOME,
            startDestination = FrameScreen.NotesScreen.route
        ) {
            composable(
                route = FrameScreen.NotesScreen.route
            ) {

            }
        }
    }
}




object AppGraph {
    const val AUTH = "authGraph"
    const val NOTES_HOME = "notesHomeGraph"
}

sealed class NoteHomeScreen(val route: String) {
    object Splash : NoteHomeScreen(route = "splash")
    object Home : NoteHomeScreen(route = "home")
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "login")
    object SignUp : AuthScreen(route = "signup")
    object Forgot : AuthScreen(route = "reset")
}

sealed class FrameScreen(val route:String) {
    object NotesScreen: FrameScreen(route = "listScreen")
    object NoteScreen: FrameScreen(route = "noteScreen")
}