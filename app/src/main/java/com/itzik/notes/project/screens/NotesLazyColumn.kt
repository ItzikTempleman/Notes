package com.itzik.notes.project.screens

import android.graphics.Path.Direction
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotesLazyColumn(
    modifier: Modifier,
    notes: MutableList<Note>,
    navHostController: NavHostController,
    noteViewModel:NoteViewModel,
    coroutineScope: CoroutineScope,
) {
    var isSwipeRemoved by remember{
        mutableStateOf(false)
    }
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(notes, {it}) { item ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            coroutineScope.launch {
                                noteViewModel.archiveANote(item)
                                notes.remove(item)
                                isSwipeRemoved = true
                                item.isInTrashBin= true
                                Log.d("TAG", "swiped: ${item.noteContent} and list siz is: ${notes.size}")
                            }
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    dismissThresholds ={ FractionalThreshold(0.2f)} ,
                    background = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                        val color by animateColorAsState(
                            targetValue = when (dismissState.targetValue) {
                                DismissValue.Default -> Color.LightGray
                                DismissValue.DismissedToEnd -> Color.Green
                                DismissValue.DismissedToStart -> Color.Red
                            }
                        )
                        val icon = when (direction){
                            DismissDirection.StartToEnd -> Icons.Default.Done
                            DismissDirection.EndToStart -> Icons.Default.Delete
                        }

                        val scale by animateFloatAsState(targetValue = if(dismissState.targetValue== DismissValue.Default) 0.8f else 1.2f)

                        val alignment= when(direction){
                            DismissDirection.EndToStart -> Alignment.CenterEnd
                            DismissDirection.StartToEnd -> Alignment.CenterStart
                        }
                        Box(modifier= Modifier.fillMaxSize().background(color)
                            .padding(horizontal = 12.dp),
                        contentAlignment = alignment
                            ){
                            Icon(icon , contentDescription ="Icon", modifier=Modifier.scale(scale) )
                        }
                    },
                    dismissContent = {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        key = "note",
                                        value = item
                                    )
                                    navHostController.navigate(route = HomeGraph.InnerNote.route)
                                },
                            elevation = animateDpAsState(targetValue = if (dismissState.dismissDirection != null) 4.dp else 0.dp).value
                        ) {
                            NoteItem(item)
                        }
                    }
                )
            }
        }
    }
