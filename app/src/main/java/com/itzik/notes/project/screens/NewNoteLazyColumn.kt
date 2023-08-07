package com.itzik.notes.project.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun NewNotesLazyColumn(
    modifier: Modifier,
    notes: MutableList<Note>,
    navHostController: NavHostController
) {

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(notes) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "note",
                            value = it
                        )
                        navHostController.navigate(route = HomeGraph.NoteScreen.route)
                    }
            ) {
                NoteItem(it)
            }
        }
    }
}
