package com.itzik.notes.project.screens.login_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.navigation.AuthGraph
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    noteViewModel: NoteViewModel
) {
    var userNameValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (welcomeText, userNameText, passwordText, signInButton, registerButton, resetPasswordText) = createRefs()


        Text(
            text = "Welcome",
            modifier = Modifier
                .constrainAs(welcomeText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(8.dp), fontFamily = FontFamily.Cursive,
            color = colorResource(id = R.color.turquoise)
        )


        OutlinedTextField(
            modifier = Modifier
                .constrainAs(userNameText) {
                    top.linkTo(welcomeText.bottom)
                }
                .padding(8.dp)
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "userName"
                )
            },
            value = userNameValue,
            label = { Text(text = "User name") },
            onValueChange = {
                userNameValue = it
            },
            placeholder = { Text(text = "user name") },
        )


        OutlinedTextField(
            modifier = Modifier
                .constrainAs(passwordText) {
                    top.linkTo(userNameText.bottom)
                }
                .padding(8.dp)
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = "password"
                )
            },
            value = passwordValue,
            label = { Text(text = "Enter password") },
            onValueChange = {
                passwordValue = it
            },
            placeholder = { Text(text = "password") },
        )



        Button(
            modifier = Modifier
                .background(colorResource(id = R.color.turquoise))
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .constrainAs(signInButton) {
                    top.linkTo(passwordText.bottom)
                },
            onClick = {
                navHostController.navigate(HomeGraph.Notes.route)
            },
        ){
            Text(text = "Sign in", color = Color.White)
        }



        Text(
            modifier = Modifier
                .clickable {

                    navHostController.navigate(AuthGraph.SignUp.route)

                }
                .constrainAs(registerButton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            text = "Not registered? Sign up",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.turquoise)
        )




        Text(
            modifier = Modifier
                .clickable {

                    navHostController.navigate(AuthGraph.Forgot.route)

                }
                .constrainAs(resetPasswordText) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(registerButton.start)
                    start.linkTo(parent.start)
                },
            text = "Forgot password",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black)
        )
    }
}



