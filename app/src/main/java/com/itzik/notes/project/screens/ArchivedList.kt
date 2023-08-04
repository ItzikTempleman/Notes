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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoFixNormal
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteForever
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
import androidx.compose.ui.text.font.FontWeight
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

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(getGradientColor())

    ) {
        val (topBar, lazyGrid, floatingActionButton) = createRefs()
        ConstraintLayout(
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .height(55.dp)
        ) {
            val (backBtn, backText, titleText) = createRefs()

            Icon(
                tint = colorResource(id = R.color.blue_green),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(backBtn) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        navHostController.navigate(HomeGraph.Notes.route)
                    },
                contentDescription = stringResource(id = R.string.back),
                painter = painterResource(id = R.drawable.back),
            )

            Text(
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(backText) {
                        start.linkTo(backBtn.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                text = stringResource(id = R.string.back_to_notes),
                color = colorResource(id = R.color.blue_green),
                fontSize = 20.sp
            )
            Text(
                fontWeight = FontWeight.Bold,
                text = stringResource(id = R.string.archived_notes),
                fontSize = 26.sp,
                color = colorResource(id = R.color.blue_green),
                modifier = modifier
                    .constrainAs(titleText) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(12.dp)
            )
        }

        Column(
            modifier = Modifier
                .constrainAs(lazyGrid) {
                    top.linkTo(topBar.bottom)
                    bottom.linkTo(parent.bottom)
                }
                .height(780.dp)
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                items(deletedNoteList) {
                    ArchivedItem(
                        deletedNoteList = deletedNoteList,
                        modifier = modifier,
                        note = it,
                        coroutineScope = coroutineScope,
                        noteViewModel = noteViewModel
                    )
                }
            })
        }
        FloatingActionButton(
            modifier = Modifier
                .constrainAs(floatingActionButton) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(12.dp),

            onClick = {
                isDialogOpen.value = true
            },
            backgroundColor = colorResource(id = R.color.blue_green),
            shape = RoundedCornerShape(120.dp),
        ) {
            Icon(
                contentDescription = null,
                imageVector = Icons.Default.Delete,
                tint = colorResource(id = R.color.white),
            )
        }
    }

}


@Composable
fun ArchivedItem(
    deletedNoteList: MutableList<Note>,
    modifier: Modifier,
    note: Note,
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
                expanded = false
            }
        ) {
            DropdownMenuItem(
                content = {
                    Text("Delete note")
                },
                onClick = {
                    coroutineScope.launch {
                        noteViewModel.deleteNoteFromEditNote(note)
                        updatedArchivedNoteList.remove(note)
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
                        updatedArchivedNoteList.remove(note)
                    }
                }
            )
        }
    }
}
