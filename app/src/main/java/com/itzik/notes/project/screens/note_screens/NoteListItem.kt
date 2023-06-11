package com.itzik.notes.project.screens.note_screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.notes.project.models.note.Note

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        val (content, time) = createRefs()
    Text(
        modifier = Modifier
            .constrainAs(content) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }.padding(8.dp),
        text=note.noteContent,
        maxLines = 1
    )

        Text(
            modifier = Modifier
                .constrainAs(time) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }.padding(8.dp),
            text=note.timeStamp,
            maxLines = 1
        )

    }
}