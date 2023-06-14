package com.itzik.notes.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.itzik.notes.project.models.note.Note
import com.itzik.notes.project.models.user.User
import com.itzik.notes.project.navigation.MainGraph.HOME
import com.itzik.notes.project.screens.note_screens.NoteList
import com.itzik.notes.project.viewmodels.NoteViewModel

@Composable
fun HomeContainer(
    user: User,
    noteViewModel: NoteViewModel,
    navHostController: NavHostController,
    noteList: MutableList<Note>,
    modifier: Modifier

){
    NavHost(
        navController = navHostController,
        route = "homeGraph",
        startDestination = HOME
    ) {
        composable(
            route = FrameScreen.NotesScreen.route
        ) {
            NoteList(
                navHostController=navHostController, noteViewModel = noteViewModel,
                user = user, modifier = modifier, noteList = emptyList<Note>() as MutableList<Note>
            )
        }
    }
}