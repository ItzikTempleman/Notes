package com.itzik.notes.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.itzik.notes.project.viewmodels.NoteViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, noteViewModel: NoteViewModel) {
    NavHost(
        navController = navController,
        startDestination = RootScreen.Splash.route
    ) {
        composable(route = RootScreen.Splash.route) {


        }
        composable(route = RootScreen.Home.route) {

        }
    }
}


sealed class RootScreen(val route: String) {
    object Splash : RootScreen(route = "splash")
    object Home : RootScreen(route = "home")
}


object Graph {
    const val ROOT = "rootGraph"
    const val LOGIN_AND_REGISTRATION = "loginAndRegistrationGraph"
    const val HOME_GRAPH = "noteGraph"
}