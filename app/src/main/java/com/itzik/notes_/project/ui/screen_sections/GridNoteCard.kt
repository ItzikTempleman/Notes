package com.itzik.notes_.project.ui.screen_sections

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.notes_.R
import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.ui.composable_elements.swipe_to_action.isRTL
import com.itzik.notes_.project.viewmodels.NoteViewModel

@Composable
fun GridNoteCard(
    note: Note,
    modifier: Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    noteColor: Int = Color.LightGray.toArgb(),
    noteViewModel: NoteViewModel,
    isSelected: Boolean,
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

    val rtl = isRTL()

    Box(
        modifier = modifier
            .height(130.dp)
            .padding(4.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(noteColor),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color.Gray,
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }

            ConstraintLayout(
                modifier = modifier
                    .fillMaxSize()
            ) {
                val (content, pinnedNoteIcon, likedNoteIcon) = createRefs()

                Text(
                    modifier = if(!rtl) Modifier
                        .constrainAs(content) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }.padding(start = 4.dp, top = 4.dp, end = 30.dp) else Modifier
                            .constrainAs(content) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }.padding(end = 4.dp, top = 4.dp, start = 30.dp) ,
                    text = note.title,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                if (isStarred) {
                    Icon(
                        modifier = Modifier.padding(bottom = 2.dp)
                            .constrainAs(likedNoteIcon) {
                                end.linkTo(parent.end, margin = 44.dp)
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
    }


