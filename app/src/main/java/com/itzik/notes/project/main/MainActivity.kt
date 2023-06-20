package com.itzik.notes.project.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.autofill.AutofillType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.itzik.notes.project.navigation.SetupNavGraph
import com.itzik.notes.project.viewmodels.NoteViewModel
import com.itzik.notes.theme.NotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            noteViewModel = viewModel()
            val coroutineScope= rememberCoroutineScope()

            NotesTheme {
                SetupNavGraph(navHostController = rememberNavController(), noteViewModel, coroutineScope = coroutineScope)
            }
        }
    }
}

