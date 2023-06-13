package com.itzik.notes.project.navigation

sealed class NoteMainScreen(
    val route:String
){
    object NotesScreen: NoteMainScreen(
        route = "listScreen"
    )

    object  NoteScreen: NoteMainScreen(
        route = "noteScreen"
    )
}


