package com.itzik.notes.project.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.navigation.HomeGraph

@Composable
fun InnerNoteScreen(

    navHostController: NavHostController,
    noteArg: Note
) {

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (editText, textField) = createRefs()
        Text(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(editText) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .clickable {
                    navHostController.navigate(HomeGraph.NoteScreen.route)
                },
            text = stringResource(id = R.string.edit),
            color = colorResource(id = R.color.black),
            fontSize = 18.sp
        )

        Text(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .constrainAs(textField) {
                    top.linkTo(editText.bottom)
                },
            text = noteArg.noteContent
        )
    }
}
