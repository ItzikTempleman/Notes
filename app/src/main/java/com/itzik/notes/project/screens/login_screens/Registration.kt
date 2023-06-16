package com.itzik.notes.project.screens.login_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel


@Composable
fun RegistrationScreen(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    var firstNameValue by remember { mutableStateOf("") }
    var familyNameValue by remember { mutableStateOf("") }
    var isInputValid = mutableStateOf(false)

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

        Button(
            modifier = Modifier
                .background(colorResource(id = R.color.turquoise))
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
            Text(text = "Done", color = Color.White)
        }
    }
}





