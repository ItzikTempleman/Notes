package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DeletedNotesScreen(
    coroutineScope: CoroutineScope,
    modifier: Modifier,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
) {

    coroutineScope.launch {
noteViewModel
    }

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (titleText, deletedNotesList) = createRefs()
        Text(
            text = "Deleted Notes",
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
            notes =,
            navHostController =
        )

    }

}