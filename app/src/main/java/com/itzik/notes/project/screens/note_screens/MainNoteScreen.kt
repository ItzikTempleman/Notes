package com.itzik.notes.project.screens.note_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.notes.R

val fontSize = mutableStateOf(12)


@Composable
fun NoteScreen() {
    var newChar by remember { mutableStateOf("") }
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

        Card(
            shape = RoundedCornerShape(6.dp), elevation = 4.dp,
            modifier = Modifier
                .width(100.dp)
                .height(44.dp)
                .padding(start = 8.dp, top = 8.dp)
                .constrainAs(fontSizeBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(30.dp)
                        .clickable {
                            fontSize.value--
                            if (fontSize.value < 8) fontSize.value = 8
                        },
                    text = "A",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(30.dp),
                    text = fontSize.value.toString(),
                    textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Bold
                )


                Text(
                    modifier = Modifier
                        .width(30.dp)
                        .clickable {
                            fontSize.value++
                            if (fontSize.value > 72) fontSize.value = 72
                        },
                    text = "A",
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(8.dp).fillMaxWidth()
                .constrainAs(headerTextField) {
                    top.linkTo(fontSizeBox.bottom)
                }
        ) {
            TextField(
                value = newChar,
                onValueChange = {
                    newChar = it
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.title),
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = colorResource(R.color.black),
                    textColor = colorResource(R.color.black),
                    disabledTextColor = colorResource(R.color.transparent),
                    backgroundColor = colorResource(R.color.white),
                    focusedIndicatorColor = colorResource(R.color.transparent),
                    unfocusedIndicatorColor = colorResource(R.color.transparent),
                    disabledIndicatorColor = colorResource(R.color.transparent),
                    focusedLabelColor = colorResource(R.color.transparent)
                )
            )
        }
    }
}
