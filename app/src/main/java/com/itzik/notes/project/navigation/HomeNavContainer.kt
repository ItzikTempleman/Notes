package com.itzik.notes.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NoteNavContainer(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
            route = "NoteListScreen",
        startDestination =

    )
}