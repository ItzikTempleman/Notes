package com.itzik.notes_.project.ui.registrations

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itzik.notes_.R
import com.itzik.notes_.project.model.User

import com.itzik.notes_.project.ui.composable_elements.CustomOutlinedTextField
import com.itzik.notes_.project.ui.navigation.Screen
import com.itzik.notes_.project.utils.getMockUser
import com.itzik.notes_.project.utils.gradientBrush
import com.itzik.notes_.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginScreen(
    coroutineScope: CoroutineScope,
    rootNavController: NavHostController,
    userViewModel: UserViewModel? = null,
) {
    var email by remember { mutableStateOf("") }
    val emailText = stringResource(id = R.string.email)
    var emailLabelMessage by remember { mutableStateOf(emailText) }
    var isEmailError by remember { mutableStateOf(false) }
    val passwordText = stringResource(id = R.string.password)
    var passwordLabelMessage by remember { mutableStateOf(passwordText) }
    var password by remember { mutableStateOf("") }
    var isPasswordError by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val tempUser = getMockUser()

    var user: State<User?>? = null

    if (userViewModel != null) {
        user = userViewModel.publicUser.collectAsState()
    }

    var isButtonEnabled by remember {
        mutableStateOf(false)
    }

    fun updateButtonState(email: String, password: String) {
        isButtonEnabled = email.isNotBlank() && password.isNotBlank()
    }

    val invalidEmail = stringResource(R.string.invalid_username_email_format)

   val invalidPassword = stringResource(R.string.enter_symbols_of_type_format)


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()

    ) {
        val (loginText, emailTF, passwordTF, loginBtn, orText, doNotHaveText, signUpBtn, loginAsGuest) = createRefs()


        Text(
            modifier = Modifier
                .constrainAs(loginText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(60.dp),
            fontSize = 32.sp,
            text = stringResource(id = R.string.hello),
            color=Color.Black
        )

        CustomOutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                updateButtonState(email, password)
            },
            label = emailLabelMessage,
            modifier = Modifier
                .constrainAs(emailTF) {
                    top.linkTo(loginText.bottom)
                }
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leftImageVector = Icons.Default.Email,
            isError = isEmailError,
            visualTransformation = VisualTransformation.None,
            readOnly = false
        )

        CustomOutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                updateButtonState(email, password)
            },
            label = passwordLabelMessage,
            modifier = Modifier
                .constrainAs(passwordTF) {
                    top.linkTo(emailTF.bottom)
                }
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leftImageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
            isError = isPasswordError,
            keyboardType = KeyboardType.Password,
            isPasswordToggleClicked = isPasswordVisible,
            isPasswordIconShowing = {
                isPasswordVisible = !isPasswordVisible
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            readOnly = false
        )

        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.intermediate_blue_10)
            ),
            modifier = Modifier
                .constrainAs(loginBtn) {
                    top.linkTo(passwordTF.bottom)
                }
                .fillMaxWidth()
                .height(90.dp)
                .padding(20.dp),
            onClick = {
                if (userViewModel != null) {
                    if (!userViewModel.validateEmail(email)) {
                        isEmailError = true
                        emailLabelMessage = invalidEmail
                    } else {
                        isEmailError = false
                        emailLabelMessage = emailText
                    }
                }
                if (userViewModel != null) {
                    if (!userViewModel.validatePassword(password)) {
                        isPasswordError = true
                        passwordLabelMessage =
                            invalidPassword
                    } else {
                        isPasswordError = false
                        passwordLabelMessage = passwordText
                    }
                }
                if (userViewModel != null) {
                    if (userViewModel.validateEmail(email) && userViewModel.validatePassword(password
                        )
                    ) {
                        coroutineScope.launch {
                            userViewModel.getUserFromUserNameAndPasswordFromOnline(
                                email,
                                password
                            ).collect { user ->
                                user.isLoggedIn = true
                                userViewModel.updateIsLoggedIn(user)
                                userViewModel.postAUser(user)
                                userViewModel.registerUser(user)
                                rootNavController.popBackStack()
                                rootNavController.navigate(Screen.Home.route)
                            }
                        }


                        if (user != null) {
                            coroutineScope.launch {
                                userViewModel.getUserFromUserNameAndPassword(
                                    email,
                                    password
                                ).collect { user ->
                                    if (user != null) {
                                        user.isLoggedIn = true
                                        userViewModel.updateIsLoggedIn(user)
                                        rootNavController.popBackStack()
                                        rootNavController.navigate(Screen.Home.route)
                                    } else {
                                        Log.e(
                                            "LoginScreen",
                                            "Invalid credentials or user not found"
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Log.e("TAG", "Invalid email or password format")
                    }
                }
            }
        ) {
            Text(
                color = Color.Black,
                fontSize = 26.sp,
                text = stringResource(id = R.string.log_in)
            )
        }




        Text(
            modifier = Modifier
                .constrainAs(doNotHaveText) {
                    bottom.linkTo(signUpBtn.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            fontSize = 22.sp,
            color = Color.DarkGray,
            text = stringResource(id = R.string.dont_have),
        )

        TextButton(
            onClick = {
                rootNavController.navigate(Screen.Registration.route)
            },
            modifier = Modifier
                .constrainAs(signUpBtn) {
                    bottom.linkTo(orText.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        ) {
            Text(
                fontSize = 26.sp,
                text = stringResource(id = R.string.register),
                color = Color.Black
            )
        }

        Text(
            modifier = Modifier.constrainAs(orText) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(loginAsGuest.top)
            },
            text = stringResource(R.string.or),
            fontSize = 22.sp,
            color = Color.DarkGray
        )

        TextButton(
            modifier = Modifier
                .constrainAs(loginAsGuest) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.padding(16.dp),
            onClick = {
                coroutineScope.launch {
                    val existingAdminUser = userViewModel?.getAdminUserIfExists(tempUser.email)

                    if (existingAdminUser != null) {
                        existingAdminUser.isLoggedIn = true
                        userViewModel.updateIsLoggedIn(existingAdminUser)
                    } else {
                        tempUser.isLoggedIn = true
                        userViewModel?.registerUser(tempUser)
                    }
                    rootNavController.navigate(Screen.Home.route)
                }
            }
        ) {
            Text(
                fontSize = 26.sp,
                text = stringResource(R.string.login_as_guest),
                color = Color.Black
            )
        }
    }
}
