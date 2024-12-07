package com.itzik.notes_.project.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.ui.composable_elements.EmptyStateMessage
import com.itzik.notes_.project.ui.composable_elements.GenericIconButton
import com.itzik.notes_.project.ui.composable_elements.swipe_to_action.GenericLazyColumn
import com.itzik.notes_.project.utils.gradientBrush
import com.itzik.notes_.project.viewmodels.NoteViewModel
import com.itzik.notes_.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.itzik.notes_.R

@SuppressLint("MutableCollectionMutableState")
@Composable
fun DeletedNotesScreen(
    modifier: Modifier,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
    userViewModel: UserViewModel,
    userId: String,
    bottomBarNavController: NavHostController,
) {
    val deletedNotes by noteViewModel.publicDeletedNoteList.collectAsState()
    var isDialogOpen by remember { mutableStateOf(false) }
    var isDeleteAllDialogOpen by remember { mutableStateOf(false) }
    var noteList by remember { mutableStateOf(deletedNotes) }

    LaunchedEffect(Unit) {
        userViewModel.fetchUserById(userId)
        noteViewModel.fetchDeletedNotes()
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = modifier.background(Color.White)
                .clickable {
                    isDeleteAllDialogOpen = false
                    isDialogOpen = false
                }
                .fillMaxSize()


            ) {
            val (returnIcon, trashBtn, deleteAllDialog, emptyStateMessage, lazyColumn) = createRefs()


            GenericIconButton(
                modifier = Modifier
                    .constrainAs(returnIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .padding(8.dp),
                onClick = {
                    bottomBarNavController.popBackStack()
                },
                imageVector = Icons.Default.ArrowBackIosNew,
                colorNumber = 4
            )

            GenericIconButton(
                modifier = Modifier
                    .constrainAs(trashBtn) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .padding(8.dp),
                onClick = {
                    isDeleteAllDialogOpen = !isDeleteAllDialogOpen
                },
                imageVector = Icons.Default.DeleteForever,
                colorNumber = 4
            )

            GenericLazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .constrainAs(lazyColumn) {
                        top.linkTo(returnIcon.bottom)
                    },
                items = deletedNotes,
                onDelete = {
                    coroutineScope.launch {
                        noteViewModel.deleteNotePermanently(it)
                    }
                },
                onRetrieve = {
                    coroutineScope.launch {
                        noteViewModel.retrieveNote(it)
                    }
                },
                noteViewModel = noteViewModel,
            )

            if (isDeleteAllDialogOpen) {
                TextButton(
                    modifier = Modifier
                        .constrainAs(deleteAllDialog) {
                            top.linkTo(lazyColumn.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(8.dp),
                    onClick = {
                        coroutineScope.launch {
                            noteViewModel.emptyTrashBin()
                        }
                        noteList = emptyList<Note>().toMutableList()
                        isDeleteAllDialogOpen = false
                    }
                ) {
                    Text(
                        stringResource(R.string.delete_all_notes),
                        color = Color.Red,
                        fontSize = 24.sp
                    )
                }
            }
            if (deletedNotes.isEmpty()) {
                EmptyStateMessage(
                    screenDescription = stringResource(id = R.string.deleted),
                    modifier = Modifier.constrainAs(emptyStateMessage) {
                        start.linkTo(parent.start)
                        top.linkTo(returnIcon.bottom)
                    }.padding(12.dp)
                )
            }
        }
    }
}


