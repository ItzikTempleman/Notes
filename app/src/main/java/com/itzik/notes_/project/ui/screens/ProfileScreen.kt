package com.itzik.notes_.project.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes_.R
import com.itzik.notes_.project.main.NoteApp

import com.itzik.notes_.project.model.Gender
import com.itzik.notes_.project.ui.composable_elements.CustomOutlinedTextField
import com.itzik.notes_.project.ui.composable_elements.EditProfileOptionsScreen
import com.itzik.notes_.project.ui.composable_elements.SelectedEditOption
import com.itzik.notes_.project.ui.navigation.Screen
import com.itzik.notes_.project.ui.screens.inner_screen_section.BottomOptionsProfileScreen
import com.itzik.notes_.project.ui.screens.inner_screen_section.BottomScreenOption
import com.itzik.notes_.project.ui.screens.inner_screen_section.ProfileField
import com.itzik.notes_.project.ui.screens.inner_screen_section.ProfileFieldRow
import com.itzik.notes_.project.ui.screens.inner_screen_section.ProfileImage
import com.itzik.notes_.project.utils.gradientBrush
import com.itzik.notes_.project.viewmodels.NoteViewModel
import com.itzik.notes_.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint(
    "CoroutineCreationDuringComposition",
    "MutableCollectionMutableState",
    "StateFlowValueCalledInComposition"
)

@Composable
fun ProfileScreen(
    coroutineScope: CoroutineScope,
    bottomBarNavController: NavHostController,
    rootNavController: NavHostController,
    userViewModel: UserViewModel,
    noteViewModel: NoteViewModel,
) {
    val user by userViewModel.publicUser.collectAsState()
    var isEditable by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(SelectedEditOption.NONE) }
    if (user != null) {
        var editedEmail by remember { mutableStateOf(user!!.email) }
        var editedPhoneNumber by remember { mutableStateOf(user!!.phoneNumber) }
        val imagePickerLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    NoteApp.instance.applicationContext.contentResolver.takePersistableUriPermission(
                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    coroutineScope.launch {
                        userViewModel.updateUserField(profileImage = it.toString())
                    }
                }
            }
        ConstraintLayout(
            modifier = Modifier.fillMaxSize().background(Color.White)

        ) {
            val (imageContainer, cancelIconModifier, editButton, editProfileOptionsList, name, email, bottomColumn) = createRefs()

            ProfileImage(
                isGuestAccount = user!!.userName == "Guest",
                imageBoxModifier = Modifier.constrainAs(imageContainer) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                imageUri = user?.profileImage,
                onImageSelected = {
                    imagePickerLauncher.launch("image/*")
                },
                onRemoveImage = {
                    userViewModel.updateUserField(profileImage = "")
                },
                cancelIconModifier = Modifier.constrainAs(cancelIconModifier) {
                    start.linkTo(imageContainer.end)
                    bottom.linkTo(imageContainer.bottom)
                }
            )


            if (user!!.userName != "Guest") {
                TextButton(modifier = Modifier
                    .constrainAs(editButton) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp), onClick = {
                    isEditable = !isEditable
                }) {
                    Text(
                        text = if (isEditable) stringResource(R.string.done)
                        else stringResource(R.string.edit),
                        color = Color.Black,
                    )
                }
            } else {
                Text(
                    color = colorResource(R.color.deep_ocean_blue),
                    text = "Cannot edit guest details",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                        .constrainAs(editButton) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }, fontSize = 20.sp
                )
            }

            if (isEditable) {
                EditProfileOptionsScreen(modifier = Modifier
                    .constrainAs(editProfileOptionsList) {
                        end.linkTo(parent.end)
                        top.linkTo(editButton.bottom)
                    }
                    .padding(end = 8.dp), onOptionSelected = { option ->
                    selectedOption = option
                    isEditable = false
                })
            }

            user?.let { currentUser ->
                Text(
                    text = currentUser.userName,
                    modifier = Modifier
                        .constrainAs(name) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .padding(start = 16.dp, top = 160.dp),
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
                Column(
                    modifier = Modifier.constrainAs(email) {
                        top.linkTo(name.bottom)
                    }, horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (selectedOption == SelectedEditOption.EMAIL) {
                            CustomOutlinedTextField(
                                leftImageVector = Icons.Outlined.Email,
                                label = "Edit user name email",
                                modifier = Modifier.width(300.dp),
                                onValueChange = { newEmail ->
                                    editedEmail = newEmail
                                },
                                value = editedEmail,
                                visualTransformation = VisualTransformation.None
                            )

                            IconButton(onClick = {
                                coroutineScope.launch {
                                    userViewModel.updateUserField(newEmail = editedEmail)
                                }
                                selectedOption = SelectedEditOption.NONE
                            }) {
                                Icon(imageVector = Icons.Outlined.Save, contentDescription = null)
                            }
                        } else {
                            user?.email?.let {
                                ProfileFieldRow(
                                    profileField = ProfileField.Email, value = it
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (selectedOption == SelectedEditOption.PHONE_NUMBER) {
                            CustomOutlinedTextField(
                                leftImageVector = Icons.Outlined.Phone,
                                label = "Edit phone number",
                                modifier = Modifier.width(300.dp),
                                onValueChange = { newPhone ->
                                    editedPhoneNumber = newPhone
                                },
                                value = editedPhoneNumber,
                                visualTransformation = VisualTransformation.None
                            )

                            IconButton(onClick = {
                                coroutineScope.launch {
                                    userViewModel.updateUserField(newPhoneNumber = editedPhoneNumber)
                                }
                                selectedOption = SelectedEditOption.NONE
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Save, contentDescription = null
                                )
                            }
                        } else {
                            user?.phoneNumber?.let {
                                ProfileFieldRow(
                                    profileField = ProfileField.PhoneNumber, value = it
                                )
                            }
                        }
                    }
                    val genderIcon: ImageVector = when (user?.gender) {
                        Gender.MALE -> Icons.Default.Male
                        Gender.FEMALE -> Icons.Default.Female
                        Gender.OTHER -> Icons.Default.Transgender
                        else -> Icons.Default.Male
                    }
                    ProfileFieldRow(
                        profileField = ProfileField.Gender,
                        value = user?.gender.toString(),
                        customIcon = genderIcon
                    )

                    user?.dateOfBirth?.let {
                        ProfileFieldRow(profileField = ProfileField.DateOfBirth,
                            value = "${user?.dateOfBirth} (age ${
                                user?.let {
                                    userViewModel.getAgeFromSDateString(
                                        it.dateOfBirth
                                    )
                                }
                            })")
                    }
                }

                BottomOptionsProfileScreen(
                    modifier = Modifier
                        .constrainAs(bottomColumn) {
                            bottom.linkTo(parent.bottom)
                        }
                        .fillMaxWidth(),
                    onItemSelected = {
                        when (it) {
                            BottomScreenOption.TRASH -> {
                                bottomBarNavController.navigate(Screen.DeletedNotesScreen.route)
                            }

                            BottomScreenOption.LOG_OUT -> {
                                coroutineScope.launch {
                                    user?.isLoggedIn = false
                                    user?.let {
                                        userViewModel.updateIsLoggedIn(
                                            it
                                        )
                                    }
                                    noteViewModel.clearAllNoteList()
                                    rootNavController.navigate(Screen.Login.route)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}