package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
@Composable
fun NoteListScreen(
    coroutineScope: CoroutineScope,
    modifier: Modifier,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
) {
    var noteList by remember { mutableStateOf(mutableListOf<Note>()) }


    coroutineScope.launch {
        noteViewModel.getAllNotes().collect {
            noteList = it

        }
    }

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (drawerScreen, noteListScreenTitle, createNote, removeAllBtn, noteListLazyColumn) = createRefs()
        DrawerScreen(coroutineScope, modifier = Modifier
            .constrainAs(drawerScreen) {
                start.linkTo(parent.start)
            }

        )
        Text(
            text = if (noteList.isNotEmpty()) stringResource(id = R.string.notes)
            else stringResource(id = R.string.no_notes),
            modifier = Modifier
                .padding(12.dp)
                .constrainAs(noteListScreenTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            fontSize = 20.sp
        )

        Icon(
            modifier = Modifier
                .padding(12.dp)
                .constrainAs(createNote) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .clickable { navHostController.navigate(HomeGraph.NoteScreen.route) },
            contentDescription = "create note",
            painter = painterResource(id = R.drawable.add_note),
        )

        Icon(
            modifier = Modifier
                .padding(12.dp)
                .constrainAs(removeAllBtn) {
                    end.linkTo(createNote.start)
                    top.linkTo(parent.top)
                }.clickable {
                    coroutineScope.launch {
                        noteViewModel.deleteAllNotes()
                        noteList = emptyList<Note>().toMutableList()
                    }
                },
            contentDescription = "delete all",
            painter = painterResource(id = R.drawable.recycle_bin),
        )


        NotesLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp)
                .constrainAs(noteListLazyColumn) {
                    top.linkTo(createNote.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            notes = noteList,
            navHostController = navHostController
        )
    }
}











