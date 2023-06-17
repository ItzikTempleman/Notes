package com.itzik.notes.project.screens.login_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.navigation.AuthGraph
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel


@Composable
fun LoginScreen(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    var userNameValue by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (welcomeText, userNameText, passwordText, signInButton, signInWithGoogleButton, signInWithFacebookButton, registerButton, resetPasswordButton) = createRefs()


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
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(id = R.string.user_name)
                )
            },
            value = userNameValue,
            label = { Text(text = stringResource(id = R.string.user_name)) },
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
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password)) },
            singleLine = true,
            placeholder = { Text(stringResource(id = R.string.password)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )


        Button(
            shape = RoundedCornerShape(20.dp),
            colors = buttonColors(colorResource(id = R.color.yellow)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .constrainAs(signInButton) {
                    top.linkTo(passwordText.bottom)
                    bottom.linkTo(parent.bottom)
                },
            onClick = {
                navHostController.navigate(HomeGraph.Notes.route)
            },
        ) {
            Text(
                text = stringResource(id = R.string.log_in),
                color = Color.White,
                fontSize = 20.sp
            )
        }



        Button(
            shape = RoundedCornerShape(20.dp),
            colors = buttonColors(colorResource(id = R.color.white)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .constrainAs(signInWithGoogleButton) {
                    top.linkTo(signInButton.bottom)
                    bottom.linkTo(signInWithFacebookButton.top)
                },
            onClick = {

            },
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (image, text) = createRefs()
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .constrainAs(image) {
                            start.linkTo(parent.start)

                        },
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = stringResource(id = R.string.sign_in_with_google)
                )
                Text(
                    modifier = Modifier
                        .constrainAs(text) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                        },
                    text = stringResource(id = R.string.sign_in_with_google),
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold,
                )
            }
        }


        Button(
            shape = RoundedCornerShape(20.dp),
            colors = buttonColors(colorResource(id = R.color.white)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .constrainAs(signInWithFacebookButton) {
                    top.linkTo(signInWithGoogleButton.bottom)
                    bottom.linkTo(registerButton.top)
                },
            onClick = {

            },
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (image, text) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.facebook_logo),
                    contentDescription = stringResource(id = R.string.sign_in_with_facebook),
                    modifier = Modifier
                        .size(22.dp)
                        .constrainAs(image) {
                            start.linkTo(parent.start)

                        }
                )
                Text(
                    modifier = Modifier
                        .constrainAs(text) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                        },
                    text = stringResource(id = R.string.sign_in_with_facebook),
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Button(
            shape = RoundedCornerShape(20.dp),
            colors = buttonColors(colorResource(id = R.color.white)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .constrainAs(registerButton) {
                    top.linkTo(signInWithFacebookButton.bottom)
                    bottom.linkTo(resetPasswordButton.top)
                },
            onClick = {
                navHostController.navigate(AuthGraph.SignUp.route)
            },
        ) {
            Text(
                text = stringResource(id = R.string.not_registered) + " ",
                color = colorResource(id = R.color.black),
                fontSize = 14.sp
            )
            Text(
                text = stringResource(id = R.string.sign_up),
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }


        Button(
            shape = RoundedCornerShape(20.dp),
            colors = buttonColors(colorResource(id = R.color.white)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .constrainAs(resetPasswordButton) {
                    top.linkTo(registerButton.bottom)
                    bottom.linkTo(parent.bottom)
                },
            onClick = {
                navHostController.navigate(AuthGraph.Forgot.route)
            },
        ) {
            Text(
                text = stringResource(id = R.string.forgot_password),
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}



