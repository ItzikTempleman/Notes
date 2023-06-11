package com.itzik.notes.project.screens.note_screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itzik.notes.project.models.note.Note

@Composable
fun NoteList(
    noteList: MutableList<Note>,
    modifier: Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(items = noteList, itemContent = {
            NoteItem(it, modifier)
        }
        )
    }
}

