package com.itzik.notes.project.screens.note_screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.models.note.Note
import com.itzik.notes.project.models.user.User
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val fontSize = mutableStateOf(16)




@Composable
fun NoteScreen(
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
    user:User
) {
    var newChar by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        val (
            backRow,
            fontSizeBox,
            doneBtn,
            contentTextField
        ) = createRefs()

        Row(
            modifier = Modifier
                .clickable {
                    navHostController.navigate(HomeGraph.Notes.route)
                }
                .padding(4.dp)
                .constrainAs(backRow) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "back",
            )
            Text(
                text = stringResource(id = R.string.notes),
                color = colorResource(id = R.color.turquoise),
                fontSize = 18.sp
            )
        }


        Text(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(doneBtn) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .clickable {
                    coroutineScope.launch {
                        saveNote(newChar, noteViewModel, user)
                        navHostController.navigate(HomeGraph.Notes.route)
                    }
                },
            text = stringResource(id = R.string.done),
            color = colorResource(id = R.color.turquoise),
            fontSize = 18.sp
        )

        Card(
            shape = RoundedCornerShape(6.dp), elevation = 4.dp,
            modifier = Modifier
                .width(100.dp)
                .height(44.dp)
                .padding(4.dp)
                .constrainAs(fontSizeBox) {
                    top.linkTo(backRow.bottom)
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
                            if (fontSize.value % 2 != 0) fontSize.value--
                            if (fontSize.value < 16) fontSize.value = 16
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
                            if (fontSize.value % 2 != 0) fontSize.value++
                            if (fontSize.value > 42) fontSize.value = 42
                        },
                    text = "A",
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        TextField(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .constrainAs(contentTextField) {
                    top.linkTo(fontSizeBox.bottom)
                },
            value = newChar,
            onValueChange = {
                newChar = it
            },
            textStyle = TextStyle.Default.copy(fontSize = fontSize.value.sp),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.content),
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = colorResource(R.color.turquoise),
                textColor = colorResource(R.color.black),
                disabledTextColor = colorResource(R.color.white),
                backgroundColor = colorResource(R.color.white),
                focusedIndicatorColor = colorResource(R.color.white),
                unfocusedIndicatorColor = colorResource(R.color.white),
                disabledIndicatorColor = colorResource(R.color.white),
                focusedLabelColor = colorResource(R.color.white)
            )
        )

    }
}

suspend fun saveNote(newChar: String, noteViewModel: NoteViewModel, user: User) {
    val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
    val note = Note(
        noteContent = newChar,
        timeStamp = time,
        isMarked = false,
        user = user
    )
    Log.d("TAG", "note: $note")
    noteViewModel.saveNote(note)
}
