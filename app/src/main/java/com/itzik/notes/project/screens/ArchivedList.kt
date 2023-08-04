package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoFixNormal
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardOptionKey
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.OpenWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.utils.getGradientColor
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint(
    "CoroutineCreationDuringComposition", "MutableCollectionMutableState",
    "SuspiciousIndentation"
)

@Composable
fun ArchivedScreen(
    coroutineScope: CoroutineScope,
    modifier: Modifier,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel = viewModel(),

    ) {

    var deletedNoteList by remember { mutableStateOf(mutableListOf<Note>()) }
    val isDialogOpen = remember { mutableStateOf(false) }


    if (isDialogOpen.value && deletedNoteList.isNotEmpty()) {
        AlertDialogScreen(isDialogOpen, noteViewModel, coroutineScope, deletedNoteList)
    }

    coroutineScope.launch {
        noteViewModel.getAllDeletedNotes().collect {
            deletedNoteList = it
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize().background(getGradientColor())

    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            val (backBtn, backText, titleText, deleteAll) = createRefs()

            Icon(
                tint = colorResource(id = R.color.blue_green),
                modifier = Modifier
                    .padding(top = 12.dp, start = 8.dp)
                    .constrainAs(backBtn) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .clickable {
                        navHostController.navigate(HomeGraph.Notes.route)
                    },
                contentDescription = stringResource(id = R.string.back),
                painter = painterResource(id = R.drawable.back),
            )

            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .constrainAs(backText) {
                        start.linkTo(backBtn.end)
                        top.linkTo(backBtn.top)
                        bottom.linkTo(backBtn.bottom)
                    },
                text = stringResource(id = R.string.back_to_notes),
                color = colorResource(id = R.color.blue_green),
                fontSize = 20.sp
            )
            Text(
                text = stringResource(id = R.string.archived_notes),
                fontSize = 20.sp,
                color = colorResource(id = R.color.blue_green),
                modifier = modifier
                    .constrainAs(titleText) {
                        end.linkTo(deleteAll.start)
                        top.linkTo(parent.top)
                    }
                    .padding(12.dp)
            )
            Icon(
                tint = colorResource(id = R.color.blue_green),
                modifier = Modifier
                    .constrainAs(deleteAll) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .clickable {
                        isDialogOpen.value = true
                    }
                    .padding(12.dp),
                contentDescription = null,
                painter = painterResource(id = R.drawable.recycle_bin),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                items(deletedNoteList) {
                    ArchivedItem(
                        deletedNoteList = deletedNoteList,
                        modifier = modifier,
                        note = it,
                        navHostController = navHostController,
                        coroutineScope = coroutineScope,
                        noteViewModel = noteViewModel
                    )
                }
            })
        }

    }
}


@Composable
fun ArchivedItem(
    deletedNoteList: MutableList<Note>,
    modifier: Modifier,
    note: Note,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
) {
    var updatedArchivedNoteList = deletedNoteList
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
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false }
        ) {
            DropdownMenuItem(
                content = {
                    Text("Delete note")
                },
                onClick = {
                    coroutineScope.launch {
                        noteViewModel.deleteNoteFromEditNote(note)
                        deletedNoteList.remove(note)
                    }
                }
            )
            DropdownMenuItem(
                content = {
                    Text("Retrieve note")
                },
                onClick = {
                    coroutineScope.launch {
                        noteViewModel.retrieveNote(note)
                        deletedNoteList.remove(note)
                    }
                }
            )
        }
    }
}
