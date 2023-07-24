package com.itzik.notes.project.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.itzik.notes.R
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AlertDialogScreen(
    isDialogOpen: MutableState<Boolean>,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
    deletedNoteList: MutableList<Note>,

    ) {
    if (isDialogOpen.value) {

        AlertDialog(
            onDismissRequest = { isDialogOpen.value = true },
            title = { Text(text = stringResource(id = R.string.delete), color = colorResource(id = R.color.white)) },
            text = {
                Text(
                    text = stringResource(id = R.string.are_you_sure),
                    color = colorResource(id = R.color.white)
                )
            },

            confirmButton = {
                TextButton(onClick = {
                    isDialogOpen.value = false
                    coroutineScope.launch {
                        noteViewModel.emptyTrashBin()
                        deletedNoteList.clear()
                    }

                }) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        color = colorResource(id = R.color.white)
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isDialogOpen.value = false
                }) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = colorResource(id = R.color.white)
                    )
                }

            },

            backgroundColor = colorResource(id = R.color.turquoise),
            contentColor = colorResource(id = R.color.white)
        )
    }
}