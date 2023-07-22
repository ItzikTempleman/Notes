package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.models.MenuItem
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
    var deletedNoteList by remember { mutableStateOf(mutableListOf<Note>()) }
    val scaffoldState = rememberScaffoldState()


    coroutineScope.launch {
        noteViewModel.getAllNotes().collect {
            noteList = it

        }
    }

    coroutineScope.launch {
        noteViewModel.getAllDeletedNotes().collect {
            deletedNoteList = it
        }
    }

    Scaffold(
        contentColor= colorResource(id = R.color.strong_yellow),
        modifier = modifier,
        scaffoldState = scaffoldState,
        drawerShape = customShape(),
        topBar = {
            TopAppBar(
                elevation= (-4).dp,
                contentColor = colorResource(id = R.color.strong_yellow),
                backgroundColor = colorResource(id = R.color.blue_green),
                title = {
                    ConstraintLayout(modifier.fillMaxWidth()) {
                        val (title, delete, addNote) = createRefs()
                        Text(
                            color = colorResource(id = R.color.strong_yellow),
                            modifier = Modifier
                                .constrainAs(title) {
                                    end.linkTo(delete.start)
                                }
                                .padding(12.dp),
                            text = if (noteList.isNotEmpty()) stringResource(id = R.string.notes)
                            else stringResource(id = R.string.no_notes),
                            fontSize = 14.sp
                        )

                        Icon(
                            modifier = Modifier
                                .constrainAs(delete) {
                                    end.linkTo(addNote.start)
                                }
                                .padding(12.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        noteViewModel.addNoteToTrashBin(noteList)
                                        noteList = emptyList<Note>().toMutableList()
                                    }
                                },
                            contentDescription = "delete all",
                            painter = painterResource(id = R.drawable.deleted),
                        )

                        Icon(
                            modifier = Modifier
                                .constrainAs(addNote) {
                                    end.linkTo(parent.end)
                                }
                                .padding(8.dp)
                                .clickable {
                                    navHostController.navigate(HomeGraph.NoteScreen.route)

                                },
                            contentDescription = "create note",
                            painter = painterResource(id = R.drawable.add_note)
                        )

                    }

                },

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
        drawerBackgroundColor = colorResource(id = R.color.white),
        drawerContent = {
            DrawerBody(
                
                items = listOf(
                    MenuItem(
                        modifier = modifier,
                        id = "Archived notes",
                        title = "Archived notes",
                        contentDescription = "",
                        vectorIcon = null,
                        imageIcon = painterResource(R.drawable.deleted_folder),
                    )
                ), onClick = {
                    navHostController.navigate(HomeGraph.Archived.route)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.blue_green))
        ) {
            Scaffold(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .clip(RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp))
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white))
            ) {
                NotesLazyColumn(
                    modifier = modifier,
                    noteViewModel = noteViewModel,
                    notes = noteList,
                    navHostController = navHostController,
                    coroutineScope = coroutineScope
                )
            }
        }
    }
}











fun customShape() = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val roundRect = RoundRect(15f, 150f, 550f, 280f, CornerRadius(20f), CornerRadius(20f), CornerRadius(20f), CornerRadius(20f))
        return Outline.Rounded(roundRect)

    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    onClick: (MenuItem) -> Unit,
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(item)
                    }
                    .padding(top = 70.dp, start = 16.dp)
            ) {
                item.vectorIcon?.let { Icon(imageVector = it, contentDescription = null) }
                item.imageIcon?.let { Icon(painter = it, contentDescription = null) }
                Text(text = item.title, modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}
