package com.itzik.notes.project.screens.login_screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.navigation.AuthGraph
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.screens.note_screens.fontSize
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.NonDisposableHandle.parent


@Composable
fun LoginScreen(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    var userNameValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (welcomeText, userNameText, passwordText, signInButton, notRegisteredText,registerButton, resetPasswordText) = createRefs()


        Text(
            fontSize = 20.sp,
            text = stringResource(id = R.string.welcome),
            modifier = Modifier
                .constrainAs(welcomeText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(8.dp),
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
                    contentDescription = stringResource(id = R.string.user_name)
                )
            },
            value = userNameValue,
            label = { Text(text =  stringResource(id = R.string.user_name)) },
            onValueChange = {
                userNameValue = it
            },
            placeholder = { Text(text = stringResource(id = R.string.user_name)) },
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
                    contentDescription = stringResource(id = R.string.password)
                )
            },
            value = passwordValue,
            label = { Text(text =  stringResource(id = R.string.password)) },
            onValueChange = {
                passwordValue = it
            },
            placeholder = { Text(text = stringResource(id = R.string.password)) },
        )



        Button(
            shape= RoundedCornerShape(20.dp),
            colors = buttonColors(colorResource(id = R.color.turquoise)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .constrainAs(signInButton) {
                    top.linkTo(passwordText.bottom)
                },
            onClick = {
                navHostController.navigate(HomeGraph.Notes.route)
            },
        ){
            Text(text = stringResource(id = R.string.log_in), color = Color.White)
        }



        Text(modifier = Modifier.padding(vertical = 10.dp)
                .constrainAs(notRegisteredText) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(registerButton.start)
                },
            text = stringResource(id = R.string.not_registered),
            fontSize = 16.sp,
        )


        Text(
            modifier = Modifier.padding(8.dp)
                .clickable {

                    navHostController.navigate(AuthGraph.SignUp.route)

                }
                .constrainAs(registerButton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            text = stringResource(id = R.string.sign_up),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )



        Text(
            modifier = Modifier.padding(8.dp)
                .clickable {

                    navHostController.navigate(AuthGraph.Forgot.route)

                }
                .constrainAs(resetPasswordText) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            text = stringResource(id = R.string.forgot_password),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}



