package com.itzik.notes.project.navigation

import android.annotation.SuppressLint
import com.itzik.notes.project.screens.NoteScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.screens.ArchivedScreen
import com.itzik.notes.project.screens.SplashScreen


import com.itzik.notes.project.screens.NoteListScreen


import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope

const val SPLASH = "splashGraph"
const val HOME = "homeGraph"


@SuppressLint("SuspiciousIndentation")
@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,

    coroutineScope: CoroutineScope,
) {
    NavHost(
        navController = navHostController,
        startDestination = SPLASH
    ) {

        navigation(
            startDestination = SplashGraph.Splash.route,
            route = SPLASH
        ) {
            composable(route = SplashGraph.Splash.route) {
                SplashScreen(navHostController)
            }
        }

        navigation(
            startDestination = HomeGraph.Notes.route,
            route = HOME
        ) {
            composable(route = HomeGraph.Notes.route) {
                NoteListScreen(
                    coroutineScope = coroutineScope,
                    modifier = Modifier,
                    navHostController,
                    noteViewModel
                )
            }

            composable(route = HomeGraph.NoteScreen.route) {
                val note = navHostController.previousBackStackEntry?.savedStateHandle?.get<Note>("note")
                NoteScreen(
                    navHostController = navHostController,
                    noteViewModel = noteViewModel,
                    coroutineScope = coroutineScope,
                    note = note
                )
            }

            composable(route = HomeGraph.NewNoteScreen.route) {
                NoteScreen(
                    navHostController = navHostController,
                    noteViewModel = noteViewModel,
                    coroutineScope = coroutineScope,
                    note = null
                )

            }
            composable(route = HomeGraph.Archived.route) {
                ArchivedScreen(

                    coroutineScope = coroutineScope,
                    modifier = Modifier,
                    navHostController = navHostController,
                    noteViewModel = noteViewModel
                )
            }
        }
    }
}


sealed class SplashGraph(val route: String) {
    object Splash : SplashGraph(route = "splash")
}

sealed class HomeGraph(val route: String) {
    object Notes : HomeGraph(route = "noteList")
    object NoteScreen : HomeGraph(route = "note")
    object NewNoteScreen : HomeGraph(route = "newNote")
    object Archived : HomeGraph(route = "archivedNotes")
}

