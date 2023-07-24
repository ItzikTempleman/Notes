package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.utils.saveNote
import com.itzik.notes.project.viewmodels.NoteViewModel
import fontSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState", "AutoboxingStateValueProperty")
@Composable
fun InnerNoteScreen(
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
    navHostController: NavHostController,
    noteArg: Note,
) {
    var newChar by remember { mutableStateOf(noteArg.noteContent) }
    val isEditClicked = mutableStateOf(false)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple))
    ) {
        val (
            topBar,
            row,
            body,
        ) = createRefs()

        ConstraintLayout(
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .height(50.dp)
        ) {
            val (
                back,
                backText,
                edit,
            ) = createRefs()

            Icon(
                tint = colorResource(id = R.color.white),
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 8.dp)
                    .constrainAs(back) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .clickable {
                        navHostController.navigate(HomeGraph.Notes.route)
                    },
                contentDescription = stringResource(id = R.string.back),
                painter = painterResource(id = R.drawable.back),
            )


            Text(
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .constrainAs(backText) {
                        start.linkTo(back.end)
                        top.linkTo(back.top)
                        bottom.linkTo(back.bottom)
                    },
                text = stringResource(id = R.string.notes),
                fontSize = 14.sp
            )

            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .constrainAs(edit) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .clickable {
                        isEditClicked.value = !isEditClicked.value
                        if(isEditClicked.value){
                            coroutineScope.launch {
                                saveNote(newChar, fontSize.value.toString(), noteViewModel)
                            }
                        }
                    },
                text = if (isEditClicked.value) stringResource(id = R.string.done) else stringResource(
                    id = R.string.edit
                ),
                color = colorResource(id = R.color.white),
                fontSize = 14.sp
            )
        }

        if (!isEditClicked.value) {
            Text(
                modifier = Modifier
                    .constrainAs(body) {
                        top.linkTo(topBar.bottom)
                    }
                    .background(colorResource(id = R.color.almost_white))
                    .fillMaxSize(),
                text = noteArg.noteContent
            )

        } else {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .padding(8.dp)
                    .constrainAs(row) {
                        top.linkTo(topBar.bottom)
                    }
            ) {
                Text(
                    color = colorResource(id = R.color.white),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(30.dp)
                        .clickable {
                            fontSize.value--
                            if (fontSize.value % 2 != 0) fontSize.value--
                            if (fontSize.value < 16) fontSize.value = 16
                        },
                    text = stringResource(id = R.string.font_size),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    color = colorResource(id = R.color.white),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(30.dp),
                    text = fontSize.value.toString(),
                    textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Bold
                )


                Text(
                    color = colorResource(id = R.color.white),
                    modifier = Modifier
                        .width(30.dp)
                        .clickable {
                            fontSize.value++
                            if (fontSize.value % 2 != 0) fontSize.value++
                            if (fontSize.value > 42) fontSize.value = 42
                        },
                    text = stringResource(id = R.string.font_size),
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            TextField(
                modifier = Modifier

                    .fillMaxSize()
                    .constrainAs(body) {
                        top.linkTo(row.bottom)
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
                    cursorColor = colorResource(R.color.black),
                    textColor = colorResource(R.color.black),
                    disabledTextColor = colorResource(R.color.white),
                    backgroundColor = colorResource(R.color.almost_white),
                    focusedIndicatorColor = colorResource(R.color.white),
                    unfocusedIndicatorColor = colorResource(R.color.white),
                    disabledIndicatorColor = colorResource(R.color.white),
                    focusedLabelColor = colorResource(R.color.white)
                )
            )
        }
    }
}
