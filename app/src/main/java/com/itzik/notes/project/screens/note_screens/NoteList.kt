package com.itzik.notes.project.screens.note_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.ImagePainter.State.Empty.painter
import com.itzik.notes.R
import com.itzik.notes.project.models.user.User
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel


@Composable
fun NoteListScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
    user: User
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (noteListScreenTitle, createNote, noteListLazyRow) = createRefs()

        Text(
            text = "${user.name}'s notes",
            modifier = Modifier
                .padding(12.dp)
                .constrainAs(noteListScreenTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            fontSize = 20.sp
        )


        Button(
            shape= RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.yellow)),
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(createNote) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            onClick = {
                navHostController.navigate(HomeGraph.Note.route)
            }
        ) {
            Row{
                Text(
                    text = stringResource(id = R.string.new_note),
                    fontSize = 14.sp
                )
                Image(
                    modifier=Modifier.padding(2.dp),
                    painter = painterResource(id = R.drawable.add_note),
                    contentDescription = stringResource(id = R.string.new_note),
                )
            }
        }



        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .constrainAs(noteListLazyRow) {
                    top.linkTo(createNote.bottom)
                }
                .padding(4.dp)
            ) {

//                items(items = noteList, itemContent = {
//                    NoteItem(it, modifier)
//                }
//                )
            }
        }
    }



