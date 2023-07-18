package com.itzik.notes.project.models

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val title: String,
    val id: String,
    val vectorIcon: ImageVector?,
    val imageIcon: Painter?,
    val contentDescription: String,
    val modifier: Modifier,

    )