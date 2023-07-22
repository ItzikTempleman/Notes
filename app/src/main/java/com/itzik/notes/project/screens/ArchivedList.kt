package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blue_green))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
        ) {
            val (backBtn, backText, titleText, deleteAll) = createRefs()

            Icon(
                tint = colorResource(id = R.color.white),
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
                    .padding(top = 8.dp)
                    .constrainAs(backText) {
                        start.linkTo(backBtn.end)
                        top.linkTo(backBtn.top)
                        bottom.linkTo(backBtn.bottom)
                    },
                text = stringResource(id = R.string.back_to_notes),
                color = colorResource(id = R.color.white),
                fontSize = 12.sp
            )
            Text(
                text = stringResource(id = R.string.archived_notes),
                fontSize = 14.sp,
                color = colorResource(id = R.color.white),
                modifier = modifier
                    .constrainAs(titleText) {
                        end.linkTo(deleteAll.start)
                        top.linkTo(parent.top)
                    }
                    .padding(8.dp)
            )
            Icon(
                tint = colorResource(id = R.color.white),
                modifier = Modifier
                    .constrainAs(deleteAll) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .clickable {
                        isDialogOpen.value = true
                    }
                    .padding(8.dp),
                contentDescription = null,

                painter = painterResource(id = R.drawable.recycle_bin),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp))
                .background(colorResource(id = R.color.white))
        ) {
            NotesLazyColumn(
                modifier = modifier,
                notes = deletedNoteList,
                navHostController = navHostController,
                coroutineScope = coroutineScope,
                noteViewModel = noteViewModel
            )
        }
    }

}