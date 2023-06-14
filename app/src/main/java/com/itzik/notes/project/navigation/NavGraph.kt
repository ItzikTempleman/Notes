package com.itzik.notes.project.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.itzik.notes.project.screens.splash_screen.AnimatedSplashScreen
import com.itzik.notes.project.viewmodels.NoteViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, noteViewModel: NoteViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            AnimatedSplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}


sealed class Screen(val route: String) {
    object Splash : Screen(route = "splash")
    object Home : Screen(route = "home")
}


object MainGraph {
    const val AUTH = "authGraph"
    const val HOME = "homeGraph"
}