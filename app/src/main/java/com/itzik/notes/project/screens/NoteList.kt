package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
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
    "UnusedMaterialScaffoldPaddingParameter"
)
@Composable
fun NoteListScreen(
    coroutineScope: CoroutineScope,
    modifier: Modifier,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
) {
    var noteList by remember { mutableStateOf(mutableListOf<Note>()) }

    val scaffoldState = rememberScaffoldState()
    coroutineScope.launch {
        noteViewModel.getAllNotes().collect {
            noteList = it

        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    ConstraintLayout(modifier.fillMaxWidth()) {
                        val (title, delete, addNote) = createRefs()
                        Text(
                            modifier = Modifier.constrainAs(title){
                                start.linkTo(parent.start)
                            }
                                .padding(12.dp),
                            text = if (noteList.isNotEmpty()) stringResource(id = R.string.notes)
                            else stringResource(id = R.string.no_notes),
                            fontSize = 20.sp
                        )

                        Icon(
                            modifier = Modifier.constrainAs(delete){
                                end.linkTo(addNote.start)
                            }
                                .padding(12.dp)

                                .clickable {
                                    coroutineScope.launch {
                                        noteViewModel.deleteAllNotes()
                                        noteList = emptyList<Note>().toMutableList()
                                    }
                                },
                            contentDescription = "delete all",
                            painter = painterResource(id = R.drawable.recycle_bin),
                        )

                        Icon(
                            modifier = Modifier.constrainAs(addNote){
                                end.linkTo(parent.end)
                            }
                                .padding(12.dp)
                                .clickable { navHostController.navigate(HomeGraph.NoteScreen.route) },
                            contentDescription = "create note",
                            painter = painterResource(id = R.drawable.add_note),
                        )

                    }

                },

                backgroundColor = colorResource(id = R.color.white),
                navigationIcon = {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                            .padding(start = 8.dp)
                    )
                }
            )
        },

        drawerContent = {
            Column(
                Modifier.width(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                        .padding(start = 8.dp, top = 20.dp, bottom = 64.dp)
                )

                Icon(
                    imageVector = Icons.Default.Recycling,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            moveToDeletedNotesScreen()
                        }
                        .padding(start = 8.dp, bottom = 16.dp)
                )
            }
        }
    ) {


        ConstraintLayout(
            modifier = modifier.fillMaxSize()
        ) {
            val (noteListLazyColumn) = createRefs()
            NotesLazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 4.dp)
                    .constrainAs(noteListLazyColumn) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                notes = noteList,
                navHostController = navHostController
            )
        }
    }
}









