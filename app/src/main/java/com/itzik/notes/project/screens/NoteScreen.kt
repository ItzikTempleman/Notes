package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.itzik.notes.project.utils.getGradientColor

import com.itzik.notes.project.viewmodels.NoteViewModel


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


val fontSize = mutableIntStateOf(18)
var lastSavedText = ""

@SuppressLint("AutoboxingStateValueProperty", "UnrememberedMutableState", "SuspiciousIndentation")
@Composable
fun NoteScreen(
    note: Note?,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
) {
    var text by remember { mutableStateOf("") }


    if (note != null) {
        text = note.noteContent
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(getGradientColor())
    ) {
        val (
            topBar,
            contentTextField,
        ) = createRefs()
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.White).padding(4.dp).clip(RoundedCornerShape(8.dp))
            .constrainAs(topBar) {
                top.linkTo(parent.top)
            }
        , elevation = 4.dp
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (backBtn, backText, minimizeText, fontSizeText, enhanceText) = createRefs()
                Icon(
                    tint = colorResource(id = R.color.blue_green),
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 8.dp)
                        .constrainAs(backBtn) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                        .clickable {
                            coroutineScope.launch {
                                if (text.isNotBlank() && text != lastSavedText) {
                                    if (note != null) {
                                        noteViewModel.deleteNoteFromEditNote(note)
                                    }
                                    noteViewModel.updateNote(text, fontSize.value.toString())
                                    lastSavedText = text
                                    text = ""
                                }
                                navHostController.popBackStack()
                            }
                        },
                    contentDescription = null,
                    painter = painterResource(id = R.drawable.back),
                )

                Text(
                    color = colorResource(id = R.color.blue_green),
                    modifier = Modifier
                        .constrainAs(backText) {
                            start.linkTo(backBtn.end)
                            top.linkTo(backBtn.top)
                            bottom.linkTo(backBtn.bottom)
                        },
                    fontWeight=FontWeight.Bold,
                    text = stringResource(id = R.string.notes),
                    fontSize = 20.sp
                )

                Text(
                    color = colorResource(id = R.color.blue_green),
                    modifier = Modifier
                        .constrainAs(minimizeText) {
                            top.linkTo(backBtn.bottom)
                            start.linkTo(parent.start)
                        }
                        .padding(top = 4.dp, start = 18.dp)
                        .width(30.dp)
                        .clickable {
                            fontSize.value--
                            if (fontSize.value % 2 != 0) fontSize.value--
                            if (fontSize.value < 18) fontSize.value = 18
                        },
                    text = stringResource(id = R.string.font_size),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(

                    color = colorResource(id = R.color.blue_green),
                    modifier = Modifier
                        .constrainAs(fontSizeText) {
                            top.linkTo(backBtn.bottom)
                            start.linkTo(minimizeText.end)
                        }
                        .padding(top = 8.dp)
                        .width(30.dp),
                    text = fontSize.value.toString(),
                    textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Bold
                )

                Text(
                    color = colorResource(id = R.color.blue_green),
                    modifier = Modifier
                        .constrainAs(enhanceText) {
                            top.linkTo(backBtn.bottom)
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
        }

        TextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
                .constrainAs(contentTextField) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            value = text,
            onValueChange = {
                text = it
            },
            textStyle = TextStyle.Default.copy(fontSize = fontSize.value.sp),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.content),
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                textColor = Color.Black,
                disabledTextColor = Color.White,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                disabledIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
            )
        )
    }
}







