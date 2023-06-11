package com.itzik.notes.project.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itzik.notes.project.screens.note_screens.NoteScreen
import com.itzik.notes.project.viewmodels.NoteViewModel
import com.itzik.notes.theme.NotesTheme

class MainActivity : ComponentActivity() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            noteViewModel= viewModel()

            NotesTheme {
               NoteScreen(noteViewModel)
            }
        }
    }
}

