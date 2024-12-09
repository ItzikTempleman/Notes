package com.itzik.notes_.project.ui.registrations

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.itzik.notes_.R
import com.itzik.notes_.project.model.Gender
import com.itzik.notes_.project.ui.composable_elements.CustomOutlinedTextField
import com.itzik.notes_.project.ui.composable_elements.GenderDropDownMenu
import com.itzik.notes_.project.ui.composable_elements.GenericIconButton
import com.itzik.notes_.project.ui.screen_sections.DatePickerDialog
import com.itzik.notes_.project.ui.navigation.Screen
import com.itzik.notes_.project.utils.reverseDateFormat
import com.itzik.notes_.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun RegistrationScreen(
    coroutineScope: CoroutineScope,
    rootNavController: NavHostController,
    userViewModel: UserViewModel? = null
) {
    val nameText = stringResource(id = R.string.full_name)
    val nameLabelMessage by remember { mutableStateOf(nameText) }
    var name by remember { mutableStateOf("") }
    val nameError by remember { mutableStateOf(false) }

    var createEmail by remember { mutableStateOf("") }
    val createEmailText = stringResource(id = R.string.create_email)
    var createEmailLabelMessage by remember { mutableStateOf(createEmailText) }
    var isNewEmailError by remember { mutableStateOf(false) }

    val createdPasswordText = stringResource(id = R.string.create_password)
    var createPassword by remember { mutableStateOf("") }
    var createPasswordLabelMessage by remember { mutableStateOf(createdPasswordText) }
    var isCreatePasswordError by remember { mutableStateOf(false) }
    var isCreatedPasswordVisible by remember { mutableStateOf(false) }

    var createPhoneNumber by remember { mutableStateOf("") }
    val createPhoneNumberText = stringResource(id = R.string.enter_phone_number)
    val createPhoneNumberLabelMessage by remember { mutableStateOf(createPhoneNumberText) }
    val phoneNumberError by remember { mutableStateOf(false) }

    var isButtonEnabled by remember { mutableStateOf(false) }
    var dropDownMenuInitialText = stringResource(R.string.select_gender)
    var dropDownMenuPlaceHolder by remember {
        mutableStateOf(dropDownMenuInitialText)
    }
    var isGenderExpanded by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf(Gender.MALE) }

    var dateSelected by remember {
        mutableStateOf("")
    }
    var isDateSelected by remember { mutableStateOf(false) }
    var isGenderSelected by remember { mutableStateOf(false) }




    fun updateButtonState(name: String, email: String, password: String, phoneNumber: String) {
        if (userViewModel != null) {
            isButtonEnabled = userViewModel.validateName(name) &&
                    userViewModel.validateEmail(email) &&
                    userViewModel.validatePassword(password) &&
                    userViewModel.validatePhoneNumber(phoneNumber)

            Log.d("TAG", "isButtonEnabled: $isButtonEnabled")
        }
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val (nameTF, emailTF, passwordTF, phoneNumberTF, genderRow, birthDate, signUpBtn) = createRefs()



        CustomOutlinedTextField(
            fieldNumber = 0,
            doesInstructionsHintExist = true,
            value = name,
            onValueChange = {
                name = it
                updateButtonState(name, createEmail, createPassword, createPhoneNumber)
            },
            label = nameLabelMessage,
            modifier = Modifier
                .constrainAs(nameTF) {
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leftImageVector = Icons.Default.Person,
            isError = nameError,
            visualTransformation = VisualTransformation.None,
            readOnly = false
        )

        CustomOutlinedTextField(
            fieldNumber = 1,
            doesInstructionsHintExist = true,
            value = createEmail,
            onValueChange = {
                createEmail = it
                updateButtonState(name, createEmail, createPassword, createPhoneNumber)
            },
            label = createEmailLabelMessage,
            modifier = Modifier
                .constrainAs(emailTF) {
                    top.linkTo(nameTF.bottom)
                }
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leftImageVector = Icons.Default.Email,
            isError = isNewEmailError,
            visualTransformation = VisualTransformation.None,
            readOnly = false
        )

        CustomOutlinedTextField(
            fieldNumber = 2,
            doesInstructionsHintExist = true,
            value = createPassword,
            onValueChange = {
                createPassword = it
                updateButtonState(name, createEmail, createPassword, createPhoneNumber)
            },
            label = createPasswordLabelMessage,
            modifier = Modifier
                .constrainAs(passwordTF) {
                    top.linkTo(emailTF.bottom)
                }
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leftImageVector = if (isCreatedPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
            isError = isCreatePasswordError,
            keyboardType = KeyboardType.Password,
            isPasswordToggleClicked = isCreatedPasswordVisible,
            isPasswordIconShowing = {
                isCreatedPasswordVisible = !isCreatedPasswordVisible
            },
            visualTransformation = if (isCreatedPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            readOnly = false
        )

        CustomOutlinedTextField(
            fieldNumber = 3,
            doesInstructionsHintExist = true,
            value = createPhoneNumber,
            onValueChange = {
                createPhoneNumber = it
                updateButtonState(name, createEmail, createPassword, createPhoneNumber)
            },
            label = createPhoneNumberLabelMessage,
            modifier = Modifier
                .constrainAs(phoneNumberTF) {
                    top.linkTo(passwordTF.bottom)
                }
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leftImageVector = Icons.Filled.Phone,
            isError = phoneNumberError,
            visualTransformation = VisualTransformation.None,
            keyboardType = KeyboardType.Phone,
            readOnly = false
        )


        Card(
            modifier = Modifier
                .constrainAs(genderRow) {
                    top.linkTo(phoneNumberTF.bottom)
                }
                .fillMaxWidth()
                .height(95.dp)
                .padding(20.dp)
                .border(
                    width = 0.2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(8.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (dropDownIcon, genderSelector, selectGenderTitle) = createRefs()

                Icon(
                    modifier = Modifier
                        .constrainAs(dropDownIcon) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                        .padding(6.dp)
                        .size(32.dp),
                    imageVector = Icons.Default.Man,
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .constrainAs(selectGenderTitle) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(dropDownIcon.end)
                        }
                        .padding(8.dp),
                    text = dropDownMenuPlaceHolder,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Box(
                    modifier = Modifier
                        .constrainAs(genderSelector) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(6.dp)
                ) {
                    GenderDropDownMenu(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = 10.dp),
                        coroutineScope = coroutineScope,
                        onDismissRequest = {
                            isGenderExpanded = false
                        },
                        isExpanded = isGenderExpanded,
                        updatedList = {
                            selectedGender = when (it) {
                                "Male" -> Gender.MALE
                                "Female" -> Gender.FEMALE
                                "Other" -> Gender.OTHER
                                else -> Gender.MALE
                            }
                            isGenderSelected = true
                            dropDownMenuPlaceHolder = it
                        }
                    )

                    GenericIconButton(
                        onClick = {
                            isGenderExpanded = !isGenderExpanded
                        },
                        colorNumber = 4,
                        imageVector = if (isGenderExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
                    )
                }
            }
        }



        DatePickerDialog(
            modifier = Modifier
                .constrainAs(birthDate) {
                    top.linkTo(genderRow.bottom)
                }
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onDateSelected = {
                dateSelected = it
                isDateSelected = true
            }
        )


        Button(
            modifier = Modifier
                .constrainAs(signUpBtn) {
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth()
                .height(90.dp)
                .padding(20.dp),
            onClick = {
                if (userViewModel != null) {
                    Log.d("TAG", "Gender selected: $isGenderSelected")
                    Log.d("TAG", "Date selected: $isDateSelected")

                    if (!userViewModel.validateEmail(createEmail)) {
                        isNewEmailError = true
                        createEmailLabelMessage = "Invalid username / email format"
                    } else {
                        isNewEmailError = false
                        createEmailLabelMessage = createEmailText
                    }

                    if (!userViewModel.validatePassword(createPassword)) {
                        isCreatePasswordError = true
                        createPasswordLabelMessage = "Enter symbols of type format X, x, $, 1"
                    } else {
                        isCreatePasswordError = false
                        createPasswordLabelMessage = createdPasswordText
                    }

                    if (userViewModel.validateEmail(createEmail) && userViewModel.validatePassword(createPassword)
                        && isGenderSelected && isDateSelected
                        && userViewModel.validateName(name) && userViewModel.validatePhoneNumber(createPhoneNumber)
                    ) {
                        val user = userViewModel.createUser(
                            name = name,
                            email = createEmail,
                            password = createPassword,
                            phoneNumber = createPhoneNumber,
                            profileImage = "",
                            gender = selectedGender,
                            dateOfBirth = dateSelected,
                            selectedWallpaper = ""
                        )

                        coroutineScope.launch {
                            try {
                                Log.d("TAG", "Inside 'registerUser': $user")
                                userViewModel.registerUser(user)
                                userViewModel.postAUser(user)
                                rootNavController.navigate(Screen.Home.route)
                            } catch (e: Exception) {
                                Log.e("TAG", "Error during registration: ${e.message}", e)
                            }
                        }
                    }
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.intermediate_blue_10)
            ),
        ) {
            Text(
                color = Color.DarkGray,
                fontSize = 22.sp,
                text = stringResource(R.string.create_user)
            )
        }
    }
}
