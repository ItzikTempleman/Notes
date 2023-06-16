package com.itzik.notes.project.screens.note_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

        Button(
            modifier = modifier
                .background(colorResource(id = R.color.white))
                .constrainAs(createNote) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .padding(4.dp),
            onClick = {
                createNewNote(navHostController)
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_note),
                    contentDescription = "add note",
                )
                Text(
                    text = stringResource(id = R.string.new_note),
                    color = colorResource(id = R.color.turquoise),
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

fun createNewNote(navHostController: NavHostController) {
    navHostController.navigate(HomeGraph.Notes.route)
}


