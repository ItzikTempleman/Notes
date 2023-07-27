package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.models.Note

import com.itzik.notes.project.viewmodels.NoteViewModel
import com.itzik.notes.project.viewmodels.saveNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


val fontSize = mutableIntStateOf(16)

@SuppressLint("AutoboxingStateValueProperty", "UnrememberedMutableState", "SuspiciousIndentation")
@Composable
fun NoteScreen(
    noteArg: Note?,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
) {

    var newChar by remember { mutableStateOf("")}

    if(noteArg!=null) newChar =noteArg.noteContent

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.turquoise))
        ) {
            val (
                backBtn,
                backText,
                fontSizeBox,
                contentTextField,
            ) = createRefs()

            Icon(
                tint = colorResource(id = R.color.white),
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 8.dp)
                    .constrainAs(backBtn) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .clickable {
                        coroutineScope.launch {
                            if (newChar.isNotBlank()) {
                                saveNote(newChar, fontSize.value.toString(), noteViewModel)
                                newChar = ""
                            }
                            navHostController.popBackStack()
                        }
                    },
                contentDescription = stringResource(id = R.string.back),
                painter = painterResource(id = R.drawable.back),
            )


            Text(
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .constrainAs(backText) {
                        start.linkTo(backBtn.end)
                        top.linkTo(backBtn.top)
                        bottom.linkTo(backBtn.bottom)
                    },
                text = stringResource(id = R.string.notes),
                fontSize = 14.sp
            )
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(colorResource(id = R.color.white))
                    .height(56.dp)
                    .constrainAs(fontSizeBox) {
                        top.linkTo(backText.bottom)
                    }
            ) {
                val (text1, fontSizeText, text2) = createRefs()
                Text(
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .constrainAs(text1) {
                            start.linkTo(parent.start)
                        }
                        .padding(top = 4.dp, start = 4.dp)
                        .width(30.dp)
                        .clickable {
                            fontSize.value--
                            if (fontSize.value % 2 != 0) fontSize.value--
                            if (fontSize.value < 16) fontSize.value = 16
                        },
                    text = stringResource(id = R.string.font_size),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(

                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .constrainAs(fontSizeText) {
                            start.linkTo(text1.end)
                        }
                        .padding(top = 8.dp)
                        .width(30.dp),
                    text = fontSize.value.toString(),
                    textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Bold
                )


                Text(
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .constrainAs(text2) {
                            start.linkTo(fontSizeText.end)
                        }
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
                    .padding(top = 30.dp)
                    .constrainAs(contentTextField) {
                        top.linkTo(fontSizeBox.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
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
                    backgroundColor = colorResource(R.color.white),
                    focusedIndicatorColor = colorResource(R.color.white),
                    unfocusedIndicatorColor = colorResource(R.color.white),
                    disabledIndicatorColor = colorResource(R.color.white),
                    focusedLabelColor = colorResource(R.color.white)
                )
            )
        }
    }






