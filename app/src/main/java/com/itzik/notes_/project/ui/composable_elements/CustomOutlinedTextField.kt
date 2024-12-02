package com.itzik.notes_.project.ui.composable_elements


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SpeakerNotesOff
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isPasswordIconShowing: ((Boolean) -> Unit)? = null,
    isPasswordToggleClicked: Boolean? = null,
    isSingleLine: Boolean = true,
) {
    var isFieldOpenState by remember {
        mutableStateOf(false)
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
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                )
            },
            leadingIcon = {
                IconButton(onClick = {
                    if (isPasswordIconShowing != null) {
                        isPasswordToggleClicked?.let {
                            isPasswordIconShowing(it)
                        }
                    } else return@IconButton
                }) {
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
                        .size(20.dp)
                        .padding(start = 8.dp),
                    onClick = {
                        isFieldOpenState = !isFieldOpenState
                    },
                    imageVector = Icons.Default.Info,
                    colorNumber = 5
                )
                if (isFieldOpenState) {
                    Text(
                        text = when (fieldNumber) {
                            0 -> "Full name, each containing at least two letters"
                            1 -> "Valid format: \"x@x.x\""
                            2 -> "\"Xx3#\" Uppercase,lowercase,number,symbol"
                            3 -> "10-12 digits number: optional '+' for country code"
                            else -> ""
                        },
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}


@Composable
fun EmptyStateMessage(
    modifier: Modifier, screenDescription: String? = ""
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

        Text(
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 20.sp,
            color = Color.Black,
            text = "No $screenDescription notes"
        )
    }
}

