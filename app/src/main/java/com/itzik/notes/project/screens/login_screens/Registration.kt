package com.itzik.notes.project.screens.login_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.screens.note_screens.fontSize
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.NonDisposableHandle.parent


@Composable
fun RegistrationScreen(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    var firstNameValue by remember { mutableStateOf("") }
    var familyNameValue by remember { mutableStateOf("") }
    val isInputValid = mutableStateOf(false)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (registrationText, nameRow, username, phoneNumber, birthday, createPassword, confirmationPassword, genderRow, createUserBtn) = createRefs()
        Text(
            text = stringResource(id = R.string.sign_up),
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(registrationText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .constrainAs(nameRow) { top.linkTo(registrationText.bottom) }) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(id = R.string.first_name)
                    )
                },
                value = firstNameValue,
                label = { Text(text = stringResource(id = R.string.first_name)) },
                onValueChange = {
                    firstNameValue = it
                },
                placeholder = { Text(text = stringResource(id = R.string.first_name)) },
            )

            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(id = R.string.family_name)
                    )
                },
                value = familyNameValue,
                label = { Text(text = stringResource(id = R.string.family_name)) },
                onValueChange = {
                    familyNameValue = it
                },
                placeholder = { Text(text = stringResource(id = R.string.family_name)) },
            )
        }

        Button(
            shape= RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.yellow)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .constrainAs(createUserBtn) {
                    top.linkTo(nameRow.bottom)
                },
            onClick = {
                if(isInputValid.value)
                navHostController.navigate(HomeGraph.Notes.route)
                else return@Button
            }
        ){
            Text(text = stringResource(id = R.string.done), color = Color.White ,fontSize = 20.sp)
        }
    }
}





