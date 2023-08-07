package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                .wrapContentHeight()
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .constrainAs(lazyGrid) {
                    top.linkTo(topBar.bottom)
                    bottom.linkTo(parent.bottom)
                }
                .height(780.dp)
                .fillMaxWidth(),
            content = {

                items(deletedNoteList) { thisNote ->
                    ArchivedItem(
                        modifier = modifier,
                        note = thisNote,
                        onRemoveNote = {
                            coroutineScope.launch {
                                deletedNoteList.remove(thisNote)
                                noteViewModel.getAllDeletedNotes().collect{
                                    deletedNoteList=it
                                }
                            }
                        },
                        coroutineScope = coroutineScope,
                        noteViewModel = noteViewModel
                    )
                }
            })

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


