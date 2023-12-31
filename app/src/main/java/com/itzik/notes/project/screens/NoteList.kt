package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.itzik.notes.project.screens.shapes.ButtonCustomShape
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
@Composable
fun NoteListScreen(
    coroutineScope: CoroutineScope,
    modifier: Modifier,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel
) {
    var noteList by remember { mutableStateOf(mutableListOf<Note>()) }

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
            text = if (noteList.isNotEmpty()) stringResource(id = R.string.notes)
            else stringResource(id = R.string.no_notes),
            modifier = Modifier
                .padding(12.dp)
                .constrainAs(noteListScreenTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            fontSize = 20.sp
        )

        ButtonCustomShape(
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.yellow)),
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(createNote) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            onclick = { navHostController.navigate(HomeGraph.NoteScreen.route) },
            painter = painterResource(id = R.drawable.add_note),
            contentDescription = stringResource(id = R.string.new_note),
            fontSize = 14.sp,
            text = stringResource(id = R.string.new_note),
            imageModifier = Modifier.padding(2.dp)
        )

        ButtonCustomShape(
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
                    noteViewModel.getAllNotes()
                }
            },
            painter = painterResource(id = R.drawable.recycle_bin),
            contentDescription = "delete all",
            fontSize = 14.sp,
            text = "",
            imageModifier = Modifier
        )

        NotesLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
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