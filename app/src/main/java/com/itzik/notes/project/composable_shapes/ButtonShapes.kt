package com.itzik.notes.project.composable_shapes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.material.Text
import androidx.compose.ui.unit.TextUnit


@Composable
fun ButtonCustomShape(
    shape: Shape,
    colors: ButtonColors,
    modifier: Modifier,
    onclick: () -> Unit,
    painter: Painter,
    contentDescription: String,
    fontSize: TextUnit,
    text: String,
imageModifier: Modifier
) {
    Button(
        shape = shape,
        colors = colors,
        modifier = modifier,
        onClick = onclick
    ) {
        Row {
            Text(
                text = text,
                fontSize = fontSize
            )
            Image(
                modifier = imageModifier,
                painter = painter,
                contentDescription = contentDescription
            )
        }
    }
}