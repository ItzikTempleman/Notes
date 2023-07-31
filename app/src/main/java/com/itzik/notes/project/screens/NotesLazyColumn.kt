package com.itzik.notes.project.screens

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.saket.swipe.rememberSwipeableActionsState

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NotesLazyColumn(
    modifier: Modifier,
    notes: MutableList<Note>,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
) {

    var isStateChanged by remember { mutableStateOf(false) }
    val swipeState = rememberSwipeableState(initialValue = 0)
    var noteList = notes

    LazyColumn(
        modifier = modifier.fillMaxSize(),
    
        ) {
        coroutineScope.launch {
            noteViewModel.getAllNotes().collect { updatedNotesList ->
                noteList = updatedNotesList
            }
        }

        items(noteList) { item ->

            val currentItem = rememberUpdatedState(newValue = item).value
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    coroutineScope.launch {
                        currentItem.isInTrashBin = true
                        noteViewModel.archiveANote(currentItem)
                        noteViewModel.getAllNotes().collect { updatedNotesList ->
                            noteList = updatedNotesList
                        }
                        isStateChanged=!isStateChanged
                    }
                    true
                }
            )

            SwipeToDismiss(
                modifier = Modifier
                    .padding(vertical = 1.dp)
                    .animateItemPlacement(),
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = { FractionalThreshold(0.2f) },

                background = {
                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                    val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)
                    val color by animateColorAsState(
                        targetValue = when (dismissState.targetValue) {
                            DismissValue.Default -> Color.LightGray
                            DismissValue.DismissedToEnd -> Color.Green
                            DismissValue.DismissedToStart -> Color.Red
                        }
                    )

                    val icon = when (direction) {
                        DismissDirection.StartToEnd -> Icons.Default.Done
                        DismissDirection.EndToStart -> Icons.Default.Delete
                    }
                    val alignment = when (direction) {
                        DismissDirection.EndToStart -> Alignment.CenterEnd
                        DismissDirection.StartToEnd -> Alignment.CenterStart
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 12.dp),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Icon",
                            modifier = Modifier.scale(scale)
                        )
                    }

                },
                dismissContent = {
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable {
                                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "note",
                                    value = currentItem
                                )
                                navHostController.navigate(route = HomeGraph.NoteScreen.route)
                            },
                        elevation = animateDpAsState(targetValue = if (dismissState.dismissDirection != null) 4.dp else 0.dp).value
                    ) {
                        NoteItem(currentItem)
                    }
                }
            )
        }
    }
}
