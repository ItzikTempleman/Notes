package com.itzik.notes.project.screens.login_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.viewmodels.NoteViewModel

@Composable
fun RegistrationScreen(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    Registration()
}


@Composable
fun Registration() {
    var firstNameValue by remember { mutableStateOf("") }
    var familyNameValue by remember { mutableStateOf("") }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (registrationText, nameRow, username, phoneNumber, birthday, createPassword, confirmationPassword, genderRow, createUserBtn) = createRefs()
        Text(
            text = "Sign up",
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .constrainAs(registrationText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            color = colorResource(id = R.color.turquoise),
            fontFamily = FontFamily.Cursive,
            fontSize = 38.sp
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
                        contentDescription = "firstName"
                    )
                },
                value = firstNameValue,
                label = { Text(text = "First name") },
                onValueChange = {
                    firstNameValue = it
                },
                placeholder = { Text(text = "name") },
            )

            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "familyName"
                    )
                },
                value = familyNameValue,
                label = { Text(text = "Family name") },
                onValueChange = {
                    familyNameValue = it
                },
                placeholder = { Text(text = "family name") },
            )

        }
    }
}


@Composable
@Preview
fun RegistrationScreenPreView() {
    Registration()
}
