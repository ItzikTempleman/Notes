package com.itzik.notes_.project.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.itzik.notes_.R
import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.model.User
import com.itzik.notes_.project.ui.composable_elements.EmptyStateMessage
import com.itzik.notes_.project.ui.composable_elements.GenericFloatingActionButton
import com.itzik.notes_.project.ui.composable_elements.SortDropDownMenu
import com.itzik.notes_.project.ui.composable_elements.swipe_to_action.GenericLazyColumn
import com.itzik.notes_.project.ui.composable_elements.swipe_to_action.isRTL
import com.itzik.notes_.project.ui.navigation.Screen
import com.itzik.notes_.project.ui.screen_sections.GridNoteCard
import com.itzik.notes_.project.ui.screens.inner_screen_section.HomeScreenTopBar
import com.itzik.notes_.project.viewmodels.NoteViewModel
import com.itzik.notes_.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.UUID


@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")

@Composable
fun HomeScreen(
    userViewModel: UserViewModel,
    userId: String,
    coroutineScope: CoroutineScope,
    bottomBarNavController: NavHostController,
    noteViewModel: NoteViewModel,
) {
    var sortType by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    val noteList by noteViewModel.publicNoteList.collectAsState()
    val pinnedNoteList by noteViewModel.publicPinnedNoteList.collectAsState()

    val selectedNote by remember { mutableStateOf<Note?>(null) }
    var isViewGrid by remember { mutableStateOf(false) }
    val user by userViewModel.publicUser.collectAsState()
    var isImagePickerOpen by remember {
        mutableStateOf(false)
    }

    var isChecked by remember {
        mutableStateOf(false)
    }

    var onlineNotes = remember {
        mutableStateListOf<Note>()
    }

    var imageSelected by remember {
        mutableStateOf("")
    }
    var allBackendUsers by remember {
        mutableStateOf(emptyList<User>())
    }
    var isImageDefault by remember {
        mutableStateOf(true)
    }
    val rtl = isRTL()

    val combinedList by remember(isChecked, pinnedNoteList, noteList) {
        mutableStateOf(
            if (isChecked) {
                onlineNotes
            } else {
                (pinnedNoteList + noteList.filter { note ->
                    !pinnedNoteList.contains(note) && !note.isInTrash && note.userId == userId
                }).distinctBy { it.noteId }
            }
        )
    }


    LaunchedEffect(userId, user) {
        launch {
            userViewModel.fetchUserById(userId)
            noteViewModel.updateUserIdForNewLogin()
            userViewModel.fetchViewType(userId)
        }

        launch {
            userViewModel.getUsersFromBackend().collect {
                allBackendUsers = it
            }
        }

        launch {
            user?.let {
                isViewGrid = it.isViewGrid
                imageSelected = it.selectedWallpaper
            }
        }
    }

    BackHandler {}



    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (backgroundImage, topRow, sortNotesLayout, noteLazyColumn, newNoteBtn, emptyStateMessage) = createRefs()


        Image(
            painter = rememberAsyncImagePainter(
                imageSelected
            ),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(backgroundImage) {
                    top.linkTo(topRow.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        HomeScreenTopBar(
            modifier = Modifier
                .constrainAs(topRow) {
                    top.linkTo(parent.top)
                }
                .fillMaxWidth(),
            onOpenWallpaperSearch = {
                isImagePickerOpen = !isImagePickerOpen
                isImageDefault = false
            },
            onSortButtonClick = {
                isExpanded = !isExpanded
            },
            onSelectView = {
                isViewGrid = !isViewGrid
                userViewModel.updateViewType(isViewGrid)
            },
            isViewGrid = mutableStateOf(isViewGrid),
            user = user,
            onChecked = {
                isChecked = it
                coroutineScope.launch {
                    noteViewModel.fetchOnlineNotes(userId).collect {
                        onlineNotes.clear()
                        onlineNotes.addAll(it)
                    }
                }
            }
        )


        SortDropDownMenu(
            isExpanded = isExpanded,
            modifier = if (!rtl) Modifier
                .wrapContentSize()
                .constrainAs(sortNotesLayout) {
                    top.linkTo(topRow.bottom)
                    start.linkTo(parent.start)
                }.padding(8.dp) else Modifier
                .wrapContentSize()
                .constrainAs(sortNotesLayout) {
                    top.linkTo(topRow.bottom)
                    end.linkTo(parent.end)
                }.padding(8.dp),
            coroutineScope = coroutineScope,
            noteViewModel = noteViewModel,
            onDismissRequest = {
                isExpanded = false
            },
            updatedSortedList = {
                sortType = it
            }
        )

        if (combinedList.isEmpty()) {
            EmptyStateMessage(
                modifier = Modifier
                    .constrainAs(emptyStateMessage) {
                        start.linkTo(parent.start)
                        top.linkTo(topRow.bottom)
                    }
                    .padding(start = 20.dp, top=30.dp)
            )
        }

        if (!isViewGrid) {
            GenericLazyColumn(
                items = combinedList,
                onPin = {
                    coroutineScope.launch {
                        noteViewModel.togglePinButton(it)
                    }
                },
                onStar = {
                    coroutineScope.launch {
                        noteViewModel.toggleStarredButton(it)
                    }
                },
                onDelete = {
                    coroutineScope.launch {
                        noteViewModel.setTrash(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .constrainAs(noteLazyColumn) {
                        top.linkTo(topRow.bottom)
                    }
                    .padding(bottom = 50.dp),
                noteViewModel = noteViewModel,
            ) { noteItem ->
                navigateToNoteScreenWithData(
                    bottomBarNavController = bottomBarNavController,
                    note = noteItem,
                    noteViewModel = noteViewModel,
                    coroutineScope = coroutineScope
                )
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.constrainAs(noteLazyColumn) {
                    top.linkTo(topRow.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
                columns = GridCells.Fixed(3),
            ) {
                items(combinedList, key = { it.noteId }) { noteItem ->
                    GridNoteCard(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                navigateToNoteScreenWithData(
                                    bottomBarNavController = bottomBarNavController,
                                    note = noteItem,
                                    noteViewModel = noteViewModel,
                                    coroutineScope = coroutineScope
                                )
                            },
                        note = noteItem,
                        noteViewModel = noteViewModel,
                        isSelected = selectedNote == noteItem,
                    )
                }
            }
        }


        GenericFloatingActionButton(
            modifier = Modifier
                .constrainAs(newNoteBtn) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }.padding(12.dp),
            onClick = {
                noteViewModel.initializeNewNote()
                bottomBarNavController.navigate(Screen.NoteScreen.route)
            },
            imageVector = Icons.Default.Add,
            containerColor = colorResource(R.color.deep_purple_2),
            iconTint = Color.White,
        )


        if (isImagePickerOpen) {
            WallpaperScreen(
                modifier = Modifier.fillMaxSize(),
                userViewModel = userViewModel,
                coroutineScope = coroutineScope,
                onImageSelected = { selectedImageUri ->
                    coroutineScope.launch {
                        if (selectedImageUri.isNotEmpty()) {
                            userViewModel.updateUserField(selectedWallpaper = selectedImageUri)
                            isImagePickerOpen = false
                        } else {
                            return@launch
                        }
                    }
                },
                onScreenExit = {
                    isImagePickerOpen = false
                },
                resetDefault = {
                    coroutineScope.launch {
                        userViewModel.updateUserField(
                            selectedWallpaper = ""
                        )
                    }
                }
            )
        }
    }
}

fun navigateToNoteScreenWithData(
    bottomBarNavController: NavHostController,
    coroutineScope: CoroutineScope,
    note: Note,
    noteViewModel: NoteViewModel,
) {
    coroutineScope.launch {
        bottomBarNavController.currentBackStackEntry?.savedStateHandle?.set(
            key = "noteId",
            value = note.noteId
        )
        noteViewModel.updateNote(
            newChar = note.content,
            noteId = note.noteId,
            userId = note.userId,
            isPinned = note.isPinned,
            isStarred = note.isStarred,
            fontSize = note.fontSize,
            fontColor = note.fontColor,
            fontWeight = note.fontWeight,
            isUpdate = true
        )
        bottomBarNavController.navigate(Screen.NoteScreen.route)
    }
}
