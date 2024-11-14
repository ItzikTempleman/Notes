package com.itzik.notes_.project.ui.registrations

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.notes_.project.ui.composable_elements.GenericIconButton

@Composable
fun RegistrationInstructionsScreen(
    isFieldOpen: Boolean = false,
    modifier: Modifier,
    selectedFieldNUmber: Int?
) {
    var isFieldOpenState = isFieldOpen
    if (isFieldOpenState) {
        Card(
            modifier = modifier.wrapContentSize()
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (cancel, text) = createRefs()
                GenericIconButton(
                    modifier = Modifier
                        .size(36.dp)
                        .constrainAs(cancel) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        },
                    onClick = {
                        isFieldOpenState = false
                    },
                    imageVector = Icons.Default.Cancel,
                    colorNumber = 4
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(text) {
                            top.linkTo(cancel.bottom)
                        },
                    text = when (selectedFieldNUmber) {
                        0 -> "User name - Two words (first name + family name) each containing at least two letters"
                        1 -> "Email - Valid email format \"x@x.x\""
                        2 -> "Password format \"Xx3*\" - at least one of the following: Upper case, lower case, number and special symbol - in any order"
                        3 -> "Phone number - A 10 digit number as a phone number - optional to add a”+” before for country code"
                        else -> ""
                    }
                )
            }
        }
    }
}
