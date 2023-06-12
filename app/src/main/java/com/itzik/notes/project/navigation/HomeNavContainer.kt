package com.itzik.notes.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun HomeContainer(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        route = "homeGraph",
        startDestination = Graph.HOME_GRAPH
    ) {

    }
}