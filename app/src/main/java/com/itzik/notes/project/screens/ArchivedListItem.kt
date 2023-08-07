package com.itzik.notes.project.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itzik.notes.R
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ArchivedItem(
    onRemoveNote: (note:Note) -> Unit,
    modifier: Modifier,
    note: Note,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(4.dp)
            .height(100.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Text(
            text = note.noteContent,
            modifier = modifier
                .padding(4.dp),
            color = colorResource(id = R.color.black),
            fontSize = 20.sp
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            DropdownMenuItem(
                content = {
                    Text(stringResource(id = R.string.delete_note))
                },
                onClick = {
                    coroutineScope.launch {
                        noteViewModel.deleteNoteFromEditNote(note)
                        onRemoveNote(note)
                    }
                }
            )
            DropdownMenuItem(
                content = {
                    Text(stringResource(id = R.string.retrieve_note))
                },
                onClick = {
                    coroutineScope.launch {
                        noteViewModel.retrieveNote(note)
                        onRemoveNote(note)
                    }
                }
            )
        }
    }
}