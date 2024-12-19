package com.itzik.notes_.project.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.itzik.notes_.R
import com.itzik.notes_.project.main.NoteApp
import com.itzik.notes_.project.ui.composable_elements.ColorPickerDialog
import com.itzik.notes_.project.ui.screens.inner_screen_section.NoteEditingTopBar
import com.itzik.notes_.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.text.isNotEmpty


@SuppressLint("SuspiciousIndentation")
@Composable
fun NoteScreen(
    noteId: Int?,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
    bottomBarNavController: NavHostController,
) {
    val note by noteViewModel.publicNote.collectAsState()
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                NoteApp.instance.applicationContext.contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                coroutineScope.launch {
                    noteViewModel.updateNote(
                        noteImage = it.toString(),
                        note.title,
                        note.content,
                        note.userId,
                        note.noteId,
                        note.isPinned,
                        note.isStarred,
                        note.fontSize,
                        note.fontColor,
                        note.fontWeight
                    )
                }
            }
        }


    var titleTextFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = note.title,
            )
        )
    }
    var contentTextFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = note.content,
            )
        )
    }

    var isColorPickerOpen by remember { mutableStateOf(false) }
    var fontSize by remember { mutableIntStateOf(note.fontSize) }
    var selectedColor by remember { mutableIntStateOf(note.fontColor) }
    val focusManager = LocalFocusManager.current

    BackHandler {
        coroutineScope.launch {
            if (contentTextFieldValue.text.isNotEmpty()) {
                noteViewModel.updateNote(
                    newTitle = titleTextFieldValue.text,
                    newContent = contentTextFieldValue.text,
                    noteId = note.noteId,
                    userId = note.userId,
                    isPinned = note.isPinned,
                    isStarred = note.isStarred,
                    fontSize = note.fontSize,
                    fontColor = note.fontColor,
                    fontWeight = note.fontWeight,
                    noteImage = note.noteImage
                )
            }
            bottomBarNavController.popBackStack()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        val (topRow, colorPickerScreen, titleTF, dividerLine, backgroundImage, contentTF) = createRefs()

        NoteEditingTopBar(
            modifier = Modifier
                .constrainAs(topRow) {
                    top.linkTo(parent.top)
                },
            onFontSizeChange = {
                coroutineScope.launch {
                    if (it && fontSize < 40) {
                        fontSize += 2
                    } else if (!it && fontSize > 10) {
                        fontSize -= 2
                    }
                    noteViewModel.updateNote(
                        newTitle = titleTextFieldValue.text,
                        newContent = contentTextFieldValue.text,
                        noteId = noteId ?: 0,
                        isPinned = note.isPinned,
                        isStarred = note.isStarred,
                        fontSize = fontSize,
                        fontColor = note.fontColor,
                        userId = note.userId,
                        fontWeight = note.fontWeight,
                        noteImage = note.noteImage
                    )
                }
            },
            onSave = {
                if (titleTextFieldValue.text.trim().isNotEmpty() && contentTextFieldValue.text.trim().isNotEmpty()) {
                    coroutineScope.launch {
                        val updatedNote = note.copy(title = titleTextFieldValue.text, content = contentTextFieldValue.text)
                        noteViewModel.saveNote(updatedNote)
                    }
                }
                bottomBarNavController.popBackStack()
            },
            isPinned = note.isPinned,
            isStarred = note.isStarred,
            onColorPickerClick = {
                isColorPickerOpen = !isColorPickerOpen
                focusManager.clearFocus()
            },
            textFieldValue = contentTextFieldValue,
            onValueChange = { newValue -> contentTextFieldValue = newValue },
            note = note,
            noteViewModel = noteViewModel,
            coroutineScope = coroutineScope,
        )

        TextField(
            maxLines = 1,
            value = titleTextFieldValue,
            onValueChange = {
                titleTextFieldValue = it
                coroutineScope.launch {
                    note.copy(title = titleTextFieldValue.annotatedString.text)
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White
            ),
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(0.dp))
                .constrainAs(titleTF) {
                    top.linkTo(topRow.bottom)
                },
            textStyle = TextStyle.Default.copy(
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            placeholder = {
                Text(
                    fontSize = 24.sp,
                    color = Color.DarkGray,
                    text = note.title.ifEmpty { stringResource(id = R.string.new_note) }
                )
            }
        )

        Row(
            modifier = Modifier
                .constrainAs(dividerLine) {
                    top.linkTo(titleTF.bottom)
                }
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            Text(text = stringResource(R.string.content), fontSize = 14.sp, color = Color.Gray)

            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(0.dp))
                .constrainAs(backgroundImage) {
                    top.linkTo(dividerLine.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
            contentDescription = null,
            painter = painterResource(R.drawable.white),
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(0.dp))
                .constrainAs(contentTF) {
                    top.linkTo(dividerLine.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                value = contentTextFieldValue,
                onValueChange = {
                    contentTextFieldValue = it
                    coroutineScope.launch {
                        note.copy(content = contentTextFieldValue.annotatedString.text)
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle.Default.copy(
                    fontSize = fontSize.sp,
                    color = Color(selectedColor),
                    fontWeight = noteViewModel.intToFontWeight(note.fontWeight)
                )
            )
            if (note.title != "") {
                IconButton(
                    modifier = Modifier.padding(top = 50.dp),
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = colorResource(R.color.deep_purple_2),
                        modifier = Modifier.size(30.dp)
                    )
                }

                Image(
                    modifier = Modifier
                        .padding(end = 20.dp, top = 20.dp)
                        .height(150.dp)
                        .width(100.dp)
                        .rotate(10f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color = Color.White)
                        .border(
                            border = BorderStroke(0.7.dp, Color.Black),
                            shape = RoundedCornerShape(4.dp)
                        ),
                    contentScale = ContentScale.FillBounds,
                    painter = rememberAsyncImagePainter(note.noteImage),
                    contentDescription = null,
                )
            }
        }

        if (isColorPickerOpen) {
            ColorPickerDialog(
                modifier = Modifier
                    .constrainAs(colorPickerScreen) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                onColorSelected = { color ->
                    coroutineScope.launch {
                        selectedColor = color.toArgb()
                        noteViewModel.updateNote(
                            newTitle = titleTextFieldValue.text,
                            newContent = contentTextFieldValue.text,
                            noteId = noteId ?: 0,
                            isStarred = note.isPinned,
                            isPinned = note.isStarred,
                            fontSize = fontSize,
                            fontColor = selectedColor,
                            userId = note.userId,
                            fontWeight = note.fontWeight,
                            noteImage = note.noteImage
                        )
                    }
                    isColorPickerOpen = false
                },
                onDismiss = {
                    isColorPickerOpen = false
                }
            )
        }
    }
}