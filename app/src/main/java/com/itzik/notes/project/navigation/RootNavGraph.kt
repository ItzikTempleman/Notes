package com.itzik.notes.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.itzik.notes.project.models.user.User
import com.itzik.notes.project.screens.note_screens.NoteScreen
import com.itzik.notes.project.viewmodels.NoteViewModel

@Composable
fun RootNavigationGraph(navController: NavHostController, noteViewModel: NoteViewModel, user: User){
    NavHost(
        navController = navController,
        route= Graph.ROOT,
        startDestination = Graph.NOTE_LIST_GRAPH
    ){
        authNavGraph(navHostController=navController)
        composable(
            route = Graph.NOTE_LIST_GRAPH
        ){
            NoteScreen(noteViewModel = noteViewModel, user =user )
        }
    }
}




object Graph {
    const val ROOT = "rootGraph"
    const val LOGIN_AND_REGISTRATION="loginAndRegistrationGraph"
    const val NOTE_LIST_GRAPH = "noteListGraph"
    const val NOTE_GRAPH = "noteGraph"
}