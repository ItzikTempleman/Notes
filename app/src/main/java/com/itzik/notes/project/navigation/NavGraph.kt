package com.itzik.notes.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itzik.notes.project.models.note.Note


import com.itzik.notes.project.screens.note_screens.InnerNoteScreen

import com.itzik.notes.project.screens.note_screens.NoteListScreen
import com.itzik.notes.project.screens.note_screens.NoteScreen
import com.itzik.notes.project.screens.AnimatedSplashScreen
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope

const val SPLASH_ROOT = "rootGraph"
const val HOME = "homeGraph"


@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,

    coroutineScope: CoroutineScope
) {
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
            startDestination = HomeGraph.Notes.route,
            route = HOME
        ) {
            composable(route = HomeGraph.Notes.route) {
                NoteListScreen(coroutineScope=coroutineScope, modifier = Modifier, navHostController, noteViewModel)
            }
            composable(route = HomeGraph.Note.route) {
                NoteScreen(navHostController, noteViewModel, coroutineScope = coroutineScope)
            }
            composable(route = HomeGraph.InnerNote.route) {

                 val noteArg= navHostController.previousBackStackEntry?.savedStateHandle?.get<Note>("note")
                if (noteArg != null) {
                    InnerNoteScreen(navHostController=navHostController, noteArg=noteArg)
                }
            }
        }
    }
}


sealed class RootSplashGraph(val route: String) {
    object Splash : RootSplashGraph(route = "splash")
}

sealed class HomeGraph(val route: String) {
    object Notes : HomeGraph(route = "noteList")
    object Note : HomeGraph(route = "note")
    object InnerNote: HomeGraph(route = "innerNote")
}

