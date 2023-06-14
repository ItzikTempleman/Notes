package com.itzik.notes.project.navigation

sealed class FrameScreen(
    val route:String
){
    object NotesScreen: FrameScreen(
        route = "listScreen"
    )

    object  NoteScreen: FrameScreen(
        route = "noteScreen"
    )
}


