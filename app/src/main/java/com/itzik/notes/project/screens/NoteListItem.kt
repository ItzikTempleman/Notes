package com.itzik.notes.project.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.notes.R
import com.itzik.notes.project.models.Note

@Composable
fun NoteItem(
    note: Note
) {
        ConstraintLayout(
            modifier = Modifier.padding(4.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (content, time) = createRefs()
            Text(
                color = colorResource(id = R.color.blue_green),
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .padding(8.dp),
                text = note.noteContent,
                maxLines = 1
            )

            Text(color = colorResource(id = R.color.blue_green),
                modifier = Modifier
                    .constrainAs(time) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp),
                text = note.timeStamp,
            )
        }
}




