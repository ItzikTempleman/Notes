package com.itzik.notes.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.itzik.notes.project.navigation.AppGraph.NOTES_HOME
import com.itzik.notes.project.screens.note_screens.NoteList
import com.itzik.notes.project.viewmodels.NoteViewModel

@Composable
fun NoteHomeScreen(
    noteViewModel: NoteViewModel,
    navHostController: NavHostController,
    modifier: Modifier

){
    NavHost(
        navController = navHostController,
        route = "homeGraph",
        startDestination = NOTES_HOME
    ) {
        composable(
            route = FrameScreen.NotesScreen.route
        ) {
            NoteList(
                navHostController=navHostController, noteViewModel = noteViewModel, modifier = modifier
            )
        }
    }
}