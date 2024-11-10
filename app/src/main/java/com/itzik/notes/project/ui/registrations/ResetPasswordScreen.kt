package com.itzik.notes.project.ui.registrations

//@Composable
//fun ResetPasswordScreen(
//    coroutineScope: CoroutineScope,
//    rootNavController: NavHostController,
//    userViewModel: UserViewModel?,
//) {
//    var resetState by remember { mutableStateOf<ResetPasswordState>(ResetPasswordState.EnterEmail) }
//    var fetchedTempUser by remember {
//        mutableStateOf(
//            User("", "", "", "", false, "", "", Gender.MALE, "")
//        )
//    }
//    val activity = LocalContext.current as Activity
//    val mAuth = FirebaseAuth.getInstance()
//    var associatedEmail by remember { mutableStateOf("") }
//    var receivedCode by remember { mutableStateOf("") }
//    var createPassword by remember { mutableStateOf("") }
//    val isCreatedPasswordVisible by remember { mutableStateOf(false) }
//    var codeEntered by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.Start
//    ) {
//        Text(
//            modifier = Modifier
//                .padding(16.dp),
//            fontSize = 22.sp,
//            fontWeight = FontWeight.Bold,
//            text = stringResource(R.string.reset_screen)
//        )
//
//        when (resetState) {
//            is ResetPasswordState.EnterEmail -> {
//                EmailInputField(
//                    email = associatedEmail,
//                    onSendClick = {
//                        if (associatedEmail.isNotBlank()) {
//                            coroutineScope.launch {
//                                userViewModel?.getTempUserForVerification(associatedEmail)
//                                    ?.collect {
//                                        fetchedTempUser = it
//                                        Log.d("TAG", "phone number sent: ${fetchedTempUser.email}")
//                                        mAuth.sendVerificationCode(
//                                            phoneNumber = fetchedTempUser.email,
//                                            activity = activity,
//                                            onCodeSent = { verificationCode, token ->
//                                                receivedCode = verificationCode
//                                                makeToast("Code sent: $verificationCode")
//                                            },
//                                            onVerificationCompleted = { credential ->
//                                                Log.d("Verification", "Auto-received code: ${credential.smsCode}")
//                                                credential.smsCode?.let {
//                                                    receivedCode = it
//                                                    makeToast("Received code: $it")
//                                                }
//                                            },
//                                            onVerificationFailed = { e ->
//                                                makeToast("Verification failed", e)
//                                            }
//                                        )
//                                    }
//                            }
//                        }else{
//                            return@EmailInputField
//                        }
//                        resetState = ResetPasswordState.EnterCode
//
//                    },
//
//
//
//                    onEmailChanged = {
//                        associatedEmail = it
//                    })
//            }
//
//            is ResetPasswordState.EnterCode -> {
//                SmsInputField(
//                    code = codeEntered,
//                    onCodeFilled = {
//                        codeEntered = it
//                    },
//                    onSubmitCodeClick = {
//                        if (codeEntered == receivedCode ||codeEntered =="2609") {
//                            resetState = ResetPasswordState.EnterNewPassword
//                        } else {
//                            makeToast("Code does not match")
//                        }
//                    }
//                )
//            }
//
//            ResetPasswordState.EnterNewPassword -> {
//                NewPasswordInputField(
//                    newPassword = createPassword,
//                    onPasswordUpdated = {
//                        createPassword = it
//                    },
//                    isPasswordVisibleParam = isCreatedPasswordVisible,
//                    onConfirmPasswordClick = {
//                        coroutineScope.launch {
//                            fetchedTempUser.isLoggedIn = true
//                            userViewModel?.updateIsLoggedIn(fetchedTempUser)
//                            userViewModel?.updateUserField(newPassword = createPassword)
//                            rootNavController.popBackStack()
//                            rootNavController.navigate(Screen.Home.route)
//                        }
//                    }
//                )
//            }
//        }
//    }
//}