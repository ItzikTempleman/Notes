package com.itzik.notes_.project.ui.composable_elements


import com.itzik.notes_.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

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
    isClickableIcon: Boolean?=false,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isPasswordIconShowing: ((Boolean) -> Unit)? = null,
    isPasswordToggleClicked: Boolean? = null,
    isSingleLine: Boolean = true,
    readOnly: Boolean,
) {
    var isFieldOpenState by remember {
        mutableStateOf(false)
    }


    val instructionText = when (fieldNumber) {
        0 -> stringResource(R.string.name_instruction)
        1 -> stringResource(R.string.email_instruction)
        2 -> stringResource(R.string.password_instruction)
        3 -> stringResource(R.string.phone_instruction)
        else -> ""
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        val (topCard, hintRow) = createRefs()
        OutlinedTextField(
            shape = MaterialTheme.shapes.small,
            value = value,
            onValueChange = { onValueChange?.invoke(it) },
            modifier = Modifier
                .constrainAs(topCard) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(),
            label = {
                Text(
                    text = label,
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                )
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, fontFamily = FontFamily.Monospace),
            readOnly=readOnly,
            leadingIcon = {
                IconButton(
                    onClick = {
                    if (isPasswordIconShowing != null) {
                        isPasswordToggleClicked?.let {
                            isPasswordIconShowing(it)
                        }
                    } else if(isClickableIcon==true){
                        invokedFunction?.invoke()
                    }
                    else return@IconButton
                }
                ) {
                    Icon(
                        imageVector = leftImageVector,
                        contentDescription = null,
                    )
                }
            },
            trailingIcon = {
                if (rightImageVector != null) {
                    IconButton(
                        onClick = {
                            invokedFunction?.invoke()
                        },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = rightImageVector,
                            contentDescription = null,
                        )
                    }
                }
            },
            singleLine = isSingleLine,
            visualTransformation = visualTransformation,
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )


        if (doesInstructionsHintExist == true) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(hintRow) {
                        top.linkTo(topCard.bottom)
                    }
                    .height(44.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                GenericIconButton(
                    modifier = Modifier
                        .size(26.dp)
                        .padding(start = 8.dp),
                    onClick = {
                        isFieldOpenState = !isFieldOpenState
                    },
                    imageVector = Icons.Outlined.Info,
                    colorNumber = 3
                )
                if (isFieldOpenState) {
                    Text(
                        text = instructionText,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}


@Composable
fun EmptyStateMessage(
    modifier: Modifier,
    screenDescription: String? = ""
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.EventNote,
            contentDescription = null,
            tint = Color.Black
        )
        val safeScreenDescription = screenDescription ?: stringResource(id = R.string.default_description)
        Text(
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 20.sp,
            color = Color.Black,
            text = stringResource(id = R.string.empty_state_message, safeScreenDescription)
        )
    }
}

