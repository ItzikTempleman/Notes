package com.itzik.notes.project.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.itzik.notes.R
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AlertDialogScreen(
    isDialogOpen: MutableState<Boolean>,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope
) {
    if (isDialogOpen.value) {

        AlertDialog(
            onDismissRequest = { isDialogOpen.value = true },
            title = { Text(text = "Deleter?", color = colorResource(id = R.color.black)) },
            text = {
                Text(
                    text = stringResource(id = R.string.are_you_sure),
                    color = colorResource(id = R.color.black)
                )
            },

            confirmButton = {
                TextButton(onClick = {
                    isDialogOpen.value = false
                    coroutineScope.launch {
                        noteViewModel.emptyTrashBin()
                    }

                }) {
                    Text(
                        text = "Confirm",
                        color = colorResource(id = R.color.black)
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isDialogOpen.value = false
                }) {
                    Text(
                        text = "Cancel",
                        color = colorResource(id = R.color.black)
                    )
                }

            },

            backgroundColor = colorResource(id = R.color.tea_green),
            contentColor = colorResource(id = R.color.white)
        )
    }
}