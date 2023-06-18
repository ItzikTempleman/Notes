package com.itzik.notes.project.screens.note_screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.notes.R
import com.itzik.notes.project.models.note.Note

@Composable
fun NoteItem(
    note: Note
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        val (content, time, line) = createRefs()
    Text(
        color = colorResource(id = R.color.black),
        modifier = Modifier
            .constrainAs(content) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            .padding(8.dp),
        text = note.noteContent,
        maxLines = 1
    )

        Text(color = colorResource(id = R.color.black),
            modifier = Modifier
                .constrainAs(time) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .padding(8.dp),
            text = note.timeStamp,
            maxLines = 1
        )
        Divider(
            modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(content.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = colorResource(id = R.color.black), thickness = 0.5.dp)
    }
}