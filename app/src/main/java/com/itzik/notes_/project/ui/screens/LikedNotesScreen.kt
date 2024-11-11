package com.itzik.notes_.project.ui.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout

import com.itzik.notes_.project.ui.composable_elements.EmptyStateMessage
import com.itzik.notes_.project.ui.composable_elements.swipe_to_action.GenericLazyColumn
import com.itzik.notes_.project.utils.gradientBrush
import com.itzik.notes_.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation", "MutableCollectionMutableState")
@Composable
fun LikedNotesScreen(
    coroutineScope: CoroutineScope,
    noteViewModel: NoteViewModel,
) {

    LaunchedEffect(Unit) {
        noteViewModel.fetchStarredNotes()
    }

    val likedNotes by noteViewModel.publicStarredNoteList.collectAsState()


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()

        ) {
        val (title, emptyStateMessage, likedNotesLazyColumn) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush(false))
        ) {}

        Icon(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(24.dp)
                .size(32.dp),
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color.Gray,
        )

        GenericLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(likedNotesLazyColumn) {
                    top.linkTo(title.bottom)
                },
            items = likedNotes,
            onRemoveStar = {
                coroutineScope.launch {
                    noteViewModel.unLikeNote(it)
                }
            },
            noteViewModel = noteViewModel,
        )

        if (likedNotes.isEmpty()) {
            EmptyStateMessage(
                screenDescription = "Liked",
                modifier = Modifier
                    .zIndex(3f)
                    .constrainAs(emptyStateMessage) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    }
            )
        }
    }
}



