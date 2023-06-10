package com.itzik.notes.project.screens.note_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.notes.R

val fontSize = mutableStateOf(12)


@Composable
fun NoteScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (
            fontSizeBox,
            headerTextField,
            contentTextField,
            addNoteBtn,
            saveNoteBtn
        ) = createRefs()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(100.dp)
                .height(30.dp)
                .padding(start = 8.dp, top = 8.dp)
                .constrainAs(fontSizeBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .border(
                    BorderStroke(0.5.dp, colorResource(id = R.color.black))
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize()

            ) {
                Text(
                    modifier = Modifier
                        .width(30.dp)
                        .clickable {
                            fontSize.value--
                            if (fontSize.value < 8) fontSize.value = 8
                        },
                    text = "-",
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .width(30.dp),
                    text = fontSize.value.toString(),
                    textAlign = TextAlign.Center
                )


                Text(
                    modifier = Modifier
                        .width(30.dp)
                        .clickable {
                            fontSize.value++
                            if (fontSize.value > 72) fontSize.value = 72

                        },
                    text = "+",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
