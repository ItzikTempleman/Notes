package com.itzik.notes.project.screens.note_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.models.note.Note
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

var noteList = mutableListOf<Note>()

@Composable
fun NoteListScreen(
    coroutineScope: CoroutineScope,
    modifier: Modifier,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel
) {


    if (noteList.isEmpty()) {
        coroutineScope.launch {
            noteViewModel.getAllNotes().collect {
                noteList = it
            }
        }
    }


    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (noteListScreenTitle, createNote, noteListLazyColumn) = createRefs()

        Text(
            text = stringResource(id = R.string.notes),
            modifier = Modifier
                .padding(12.dp)
                .constrainAs(noteListScreenTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            fontSize = 20.sp
        )


        Button(
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.yellow)),
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(createNote) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            onClick = {
                navHostController.navigate(HomeGraph.Note.route)
            }
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.new_note),
                    fontSize = 14.sp
                )
                Image(
                    modifier = Modifier.padding(2.dp),
                    painter = painterResource(id = R.drawable.add_note),
                    contentDescription = stringResource(id = R.string.new_note),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(noteListLazyColumn) {
                    top.linkTo(createNote.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(4.dp)
        ) {
            NoteListLazyColumn() {
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "note",
                    value = it
                )
                navHostController.navigate(route = HomeGraph.InnerNote.route)
            }
        }
    }
}

@Composable
fun NoteListLazyColumn(selectedNote: (Note) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(noteList, itemContent = {
            Surface(modifier = Modifier
                .fillMaxHeight()
                .clickable {
                    selectedNote(it)
                }
            ) {
               NoteItem(it)
            }
        })
    }
}



