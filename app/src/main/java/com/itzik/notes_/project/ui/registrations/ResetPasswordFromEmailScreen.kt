package com.itzik.notes_.project.ui.registrations

//
//@Composable
//fun ResetPasswordFromEmailScreen(
//    rootNavController: NavHostController,
//    userViewModel: UserViewModel,
//    coroutineScope: CoroutineScope
//) {
//    var email by remember { mutableStateOf("") }
//    var message by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Reset Password",
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            modifier = Modifier.fillMaxWidth(),
//            singleLine = true
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                sendPasswordResetLink(
//                    email = email,
//                    onSuccess = { link ->
//                        message = "Reset link generated: $link"
//                    },
//                    onFailure = { error ->
//                        message = error
//                    }
//                )
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Send Reset Email")
//        }
//
//        if (message.isNotEmpty()) {
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(text = message, color = MaterialTheme.colorScheme.error)
//        }
//    }
//}
//
//
//fun sendPasswordResetLink(
//    email: String,
//    onSuccess: (String) -> Unit,
//    onFailure: (String) -> Unit
//) {
//    val apiKey = "AIzaSyBC6o6FgGE4c_84V1fjEbLVYuqevMlBJsQ"
//
//    FirebasePasswordResetManager.generatePasswordResetLink(
//        email = email,
//        apiKey = apiKey,
//        onSuccess = { resetLink ->
//            onSuccess(resetLink)
//        },
//        onFailure = { errorMessage ->
//            onFailure(errorMessage)
//        }
//    )
//}
//
//object FirebasePasswordResetManager {
//    private val client = OkHttpClient()
//
//    fun generatePasswordResetLink(
//        email: String,
//        apiKey: String,
//        onSuccess: (String) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        val url = "https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=$apiKey"
//        val json = JSONObject().apply {
//            put("requestType", "PASSWORD_RESET")
//            put("email", email)
//        }.toString()
//
//        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
//        val request = Request.Builder()
//            .url(url)
//            .post(requestBody)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onResponse(call: Call, response: Response) {
//                response.use {
//                    if (response.isSuccessful) {
//                        val responseJson = JSONObject(response.body?.string() ?: "")
//                        val resetLink = responseJson.optString("oobLink")
//                        if (resetLink.isNotEmpty()) {
//                            onSuccess(resetLink)
//                        } else {
//                            onFailure("Failed to retrieve the password reset link.")
//                        }
//                    } else {
//                        val errorBody = response.body?.string()
//                        onFailure("Error: ${response.message}. Details: $errorBody")
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                onFailure("Network request failed: ${e.message}")
//            }
//        })
//    }
//}