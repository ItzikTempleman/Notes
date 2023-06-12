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
        startDestination = Graph.HOME_GRAPH
    ){
        authNavGraph(navHostController=navController)
        composable(
            route = Graph.HOME_GRAPH
        ){
            NoteScreen(noteViewModel = noteViewModel, user =user )
        }
    }
}




object Graph {
    const val ROOT = "rootGraph"
    const val LOGIN_AND_REGISTRATION="loginAndRegistrationGraph"
    const val HOME_GRAPH = "noteGraph"
}