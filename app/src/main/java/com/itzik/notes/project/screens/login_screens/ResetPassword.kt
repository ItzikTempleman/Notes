package com.itzik.notes.project.screens.login_screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.screens.note_screens.fontSize
import com.itzik.notes.project.viewmodels.NoteViewModel

@Composable
fun ResetPasswordScreen(
    navHostController: NavHostController, noteViewModel: NoteViewModel
) {

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (resetPasswordScreenTitle) = createRefs()
        Text(
            text = stringResource(id = R.string.reset_password),
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(resetPasswordScreenTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            fontSize = 20.sp
        )
    }
}