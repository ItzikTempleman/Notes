package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
@Composable
fun ArchivedScreen(
    coroutineScope: CoroutineScope,
    modifier: Modifier,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
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

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (titleText, deleteAll,deletedNotesList) = createRefs()
        Text(
            text = stringResource(id = R.string.archived_notes),
            fontSize = 20.sp,
            color = colorResource(id = R.color.black),
            modifier = modifier.constrainAs(titleText) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }.padding(8.dp)
        )
        Icon(
            modifier = Modifier
                .constrainAs(deleteAll) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .clickable {
                    isDialogOpen.value=true
                }.padding(8.dp),
            contentDescription = null,
            painter = painterResource(id = R.drawable.recycle_bin),
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