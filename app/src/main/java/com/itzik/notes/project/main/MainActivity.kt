package com.itzik.notes.project.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.itzik.notes.project.screens.note_screens.NoteScreen
import com.itzik.notes.theme.NotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                NoteScreen()
            }
        }
    }
}

