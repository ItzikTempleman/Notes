package com.itzik.notes.project.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.navigation.HomeGraph

@Composable
fun NotesLazyColumn(
    modifier: Modifier,
    notes:MutableList<Note>,
navHostController:NavHostController
){
    Column(
        modifier = modifier
    ) {
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(notes, itemContent = {
                Surface(modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(key = "note", value = it)
                        navHostController.navigate(route = HomeGraph.NoteScreen.route)
                    }) {
                    NoteItem(it)
                }
            })
        }
    }
}