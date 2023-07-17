package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
@Composable
fun TrashBinScreen(
    coroutineScope: CoroutineScope,
    modifier: Modifier,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
) {
    var deletedNoteList by remember { mutableStateOf(mutableListOf<Note>()) }

    coroutineScope.launch {
        noteViewModel.getAllDeletedNotes().collect {
            deletedNoteList = it
        }
    }

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (titleText, deletedNotesList) = createRefs()
        Text(
            text = "Trash Bin",
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.Blue,
            modifier = modifier.constrainAs(titleText) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
        )

        NotesLazyColumn(
            modifier = modifier.constrainAs(deletedNotesList) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(titleText.bottom)
            },
            notes = deletedNoteList,
            navHostController = navHostController
        )

    }

}