package com.itzik.notes_.project.ui.screens.inner_screen_section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.notes_.R
import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.ui.composable_elements.BoldedTextSelectionButtons
import com.itzik.notes_.project.ui.composable_elements.GenericIconButton
import com.itzik.notes_.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun NoteEditingTopBar(
    modifier: Modifier,
    onFontSizeChange: (Boolean) -> Unit,
    onSave: () -> Unit,
    isPinned: Boolean,
    isStarred: Boolean,
    onColorPickerClick: () -> Unit,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    note: Note,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        val (backIcon, decreaseFont, increaseFont, leftDivider, unBold, bold, midDivider, colorPicker, pin, star) = createRefs()

        GenericIconButton(
            modifier = Modifier
                .constrainAs(backIcon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 12.dp)
                .size(50.dp),
            onClick = onSave,
            imageVector = Icons.Default.ArrowBackIosNew,
            colorNumber = 4,
        )


        Text(
            modifier = Modifier
                .constrainAs(decreaseFont) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(backIcon.end)
                }
                .padding(8.dp)
                .clickable {
                    onFontSizeChange(false)
                },
            text = "A",
            fontSize = 20.sp,
            color = Color.Black
        )

        Text(
            modifier = Modifier
                .constrainAs(increaseFont) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(decreaseFont.end)
                }
                .padding(8.dp)
                .clickable {
                    onFontSizeChange(true)
                },
            text = "A",
            fontSize = 28.sp,
            color = Color.Black
        )

        VerticalDivider(
            modifier = Modifier
                .constrainAs(leftDivider) {
                    start.linkTo(increaseFont.end)
                }
                .padding(vertical = 12.dp),
            color = Color.Black
        )


        BoldedTextSelectionButtons(
            modifier = Modifier.constrainAs(unBold) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(leftDivider.end)
            },
            isBolded = false,
            contentTextFieldValue = textFieldValue,
            onValueChange = onValueChange,
            note = note,
            noteViewModel = noteViewModel,
            coroutineScope = coroutineScope
        )

        BoldedTextSelectionButtons(
            modifier = Modifier.constrainAs(bold) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(unBold.end)
            },
            isBolded = true,
            contentTextFieldValue = textFieldValue,
            onValueChange = onValueChange,
            note = note,
            noteViewModel = noteViewModel,
            coroutineScope = coroutineScope
        )


        VerticalDivider(
            modifier = Modifier
                .constrainAs(midDivider) {
                    start.linkTo(bold.end)
                }
                .padding(vertical = 12.dp), color = Color.Black
        )


        IconButton(
            modifier = Modifier.constrainAs(colorPicker) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(midDivider.end)
            },
            onClick = onColorPickerClick
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.color_palette),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

        if (isPinned) {
            Icon(
                modifier = Modifier
                    .constrainAs(pin) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 8.dp)
                    .rotate(45f),
                imageVector = Icons.Outlined.PushPin,
                contentDescription = null,
                tint = Color.Gray
            )
        }

        if (isStarred) {
            Icon(
                modifier = Modifier
                    .constrainAs(star) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 60.dp),
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}
