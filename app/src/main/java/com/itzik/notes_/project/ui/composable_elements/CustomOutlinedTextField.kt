package com.itzik.notes_.project.ui.composable_elements


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.notes_.project.ui.registrations.RegistrationInstructionsScreen

@Composable
fun CustomOutlinedTextField(
    fieldNumber: Int? = null,
    doesInstructionsHintExist: Boolean? = false,
    invokedFunction: (() -> Unit)? = null,
    value: String,
    onValueChange: ((String) -> Unit)? = null,
    label: String,
    modifier: Modifier = Modifier,
    leftImageVector: ImageVector,
    rightImageVector: ImageVector? = null,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isPasswordIconShowing: ((Boolean) -> Unit)? = null,
    isPasswordToggleClicked: Boolean? = null,
    isSingleLine: Boolean = true,
    isFieldOpenState: Boolean = false
) {
    var isFieldOpen = isFieldOpenState
    Card(
        modifier = if(doesInstructionsHintExist == true) modifier.fillMaxWidth().height(80.dp) else modifier.fillMaxWidth().height(60.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (textFiled, instructions) = createRefs()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(textFiled) {
                        top.linkTo(parent.top)
                    },
                verticalArrangement = if(doesInstructionsHintExist==true )Arrangement.Top else Arrangement.Center
            ) {
                OutlinedTextField(
                    shape = MaterialTheme.shapes.small,
                    value = value,
                    onValueChange = { onValueChange?.invoke(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = label,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                        )
                    },
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                if (isPasswordIconShowing != null) {
                                    isPasswordToggleClicked?.let {
                                        isPasswordIconShowing(it)
                                    }
                                } else return@IconButton
                            }) {
                            Icon(
                                imageVector = leftImageVector,
                                contentDescription = null,
                                tint = Color.Black,
                            )
                        }
                    },
                    trailingIcon = {
                        if (rightImageVector != null) {
                            IconButton(
                                onClick = {
                                    invokedFunction?.invoke()
                                },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Icon(
                                    imageVector = rightImageVector,
                                    contentDescription = null,
                                    tint = Color.Black,
                                )
                            }
                        }
                    },
                    singleLine = isSingleLine,
                    visualTransformation = visualTransformation,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        errorLabelColor = MaterialTheme.colorScheme.error
                    ),
                    isError = isError,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
                )
            }

            if (doesInstructionsHintExist == true) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(instructions) {
                            top.linkTo(textFiled.bottom)
                        }
                        .height(24.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    GenericIconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = {
                            isFieldOpen = true
                        },
                        imageVector = Icons.Default.QuestionMark,
                        colorNumber = 1
                    )
                }
           }
            if (isFieldOpen) {
                RegistrationInstructionsScreen(
                    selectedFieldNUmber = fieldNumber
                )
            }
        }
    }
}

@Composable
fun EmptyStateMessage(
    modifier: Modifier,
    screenDescription: String? = ""
) {
    Text(
        modifier = modifier,
        fontSize = 40.sp,
        color = Color.Gray,
        text = "No $screenDescription notes"
    )
}

