package com.itzik.notes_.project.ui.screen_sections

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.itzik.notes_.R
import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.utils.getFormattedTime
import com.itzik.notes_.project.viewmodels.NoteViewModel
import java.nio.file.WatchEvent

@SuppressLint("SuspiciousIndentation")
@Composable
fun NoteListItem(
    modifier: Modifier,
    note: Note,
    noteViewModel: NoteViewModel,
    isSelected: Boolean? = null,
) {
    var isOptionVisible by remember {
        mutableStateOf(isSelected)
    }
    LaunchedEffect(isSelected) {
        isOptionVisible = isSelected
    }
    val pinStateMap by noteViewModel.publicPinStateMap.collectAsState()
    val starStateMap by noteViewModel.publicStarStateMap.collectAsState()
    val isPinned = pinStateMap[note.noteId] ?: false
    val isStarred = starStateMap[note.noteId] ?: false


        ConstraintLayout(
            modifier = modifier.fillMaxWidth()
                .height(50.dp)
        ) {
            val (timeStamp, content, pinnedNoteIcon, likedNoteIcon) = createRefs()

            Box(
                modifier = Modifier  .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .constrainAs(timeStamp) {
                        start.linkTo(parent.start, margin = 8.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .border(0.5.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp),

            ) {
                Text(
                    getFormattedTime(), fontSize = 10.sp,
                    color=Color.Black
                )
            }


            Text(
                maxLines = 1,
                modifier = Modifier.constrainAs(content) {
                    start.linkTo(timeStamp.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(2f/3f)
                }.padding(start = 8.dp),
                text = note.content,
                fontSize = note.fontSize.sp,
                color = Color(note.fontColor),
                fontWeight = noteViewModel.intToFontWeight(note.fontWeight)
            )

            if (isStarred) {
                Icon(
                        modifier = Modifier
                            .constrainAs(likedNoteIcon) {
                                end.linkTo(parent.end, margin = 44.dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                            .size(20.dp),
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = colorResource(R.color.muted_yellow)
                    )
            }
            if (isPinned) {
                Icon(
                    imageVector = Icons.Default.PushPin,
                    modifier = Modifier
                        .constrainAs(pinnedNoteIcon) {
                            end.linkTo(parent.end, margin = 12.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(20.dp)
                        .rotate(45f),
                    tint = colorResource(R.color.deep_ocean_blue),
                    contentDescription = null
                )
            }
        }
    }


