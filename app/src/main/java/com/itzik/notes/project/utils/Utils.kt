package com.itzik.notes.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import com.itzik.notes.R

object Constants {
    const val NOTE_TABLE = "noteTable"
    const val NOTE_DATABASE = "noteDatabase"
}

@Composable
fun getGradientColor(): Brush {

    return Brush.linearGradient(colors = listOf(
        colorResource(id = R.color.very_light_blue),
        colorResource(id = R.color.red),
        colorResource(id = R.color.light_teal)
    ), start = Offset(50f, 50f), end = Offset(50f, 50f))
}