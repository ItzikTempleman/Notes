package com.itzik.notes.project.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itzik.notes.project.models.user.Gender
import com.itzik.notes.project.models.user.User
import com.itzik.notes.project.screens.note_screens.NoteScreen
import com.itzik.notes.project.viewmodels.NoteViewModel
import com.itzik.notes.theme.NotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            noteViewModel = viewModel()

            user = User(
                "Itzik", "Templeman",
                "26/09/91", "0545408531",
                "itzik.templeman@gmail.com", "Qwerty1024",
                "203490495", Gender.MALE
            )

            NotesTheme {
                NoteScreen(noteViewModel, user)
            }
        }
    }
}

