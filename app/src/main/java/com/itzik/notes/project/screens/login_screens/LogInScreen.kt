package com.itzik.notes.project.screens.login_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(
    signUp:()->Unit,
    login:()->Unit,
    forgotPassword:()->Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.clickable {
                login()
            },
            text = "Log in"
        )
        Text(
            modifier = Modifier.clickable {
                signUp()
            },
            text = "Sign up"

            )
        Text(
            modifier = Modifier.clickable {
                forgotPassword()
            },
            text = "Forgot password"

            )
    }
}



