package com.itzik.notes.project.screens.note_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel


@Composable
fun NoteListScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (createNote, noteListLazyRow) = createRefs()

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .clickable {
                    navHostController.navigate(HomeGraph.Note.route)
                }
                .padding(4.dp)
                .constrainAs(createNote) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        ) {
            Text(
                text = stringResource(id = R.string.new_note),
                color = colorResource(id = R.color.turquoise),
                fontSize = 18.sp
            )
            Image(
                painter = painterResource(id = R.drawable.add_note),
                contentDescription = "back",
            )
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



