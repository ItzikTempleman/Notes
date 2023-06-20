package com.itzik.notes.project.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.screens.shapes.ButtonShapeScreen
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
        val (noteListScreenTitle, createNote, removeAllBtn, noteListLazyColumn) = createRefs()

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



        ButtonShapeScreen(
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.yellow)),
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(createNote) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            onclick = { navHostController.navigate(HomeGraph.Note.route) },
            painter = painterResource(id = R.drawable.add_note),
            contentDescription = stringResource(id = R.string.new_note),
            fontSize = 14.sp,
            text = stringResource(id = R.string.new_note),
            imageModifier = Modifier.padding(2.dp)
        )

        ButtonShapeScreen(
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(removeAllBtn) {
                    end.linkTo(createNote.start)
                    top.linkTo(parent.top)
                },
            onclick = {
                coroutineScope.launch {
                    noteViewModel.deleteAllNotes()
                    noteViewModel.deleteNotesFromList(noteList)
                }
            },
            painter = painterResource(id = R.drawable.recycle_bin),
            contentDescription = "delete all",
            fontSize = 14.sp,
            text = "",
            imageModifier = Modifier
        )


        val mutableNoteList by remember { mutableStateOf(noteList) }
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
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(mutableNoteList, itemContent = {
                    Surface(modifier = Modifier.fillMaxHeight().clickable {
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(key = "note", value = it)
                        navHostController.navigate(route = HomeGraph.InnerNote.route)
                    }
                    ) {
                        NoteItem(it)
                    }
                })
            }
        }
    }
}












