package com.itzik.notes.project.models

import android.os.Parcelable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itzik.notes.project.utils.Constants
import kotlinx.parcelize.Parcelize

data class MenuItem(
    val title: String,
    val vectorIcon: ImageVector?,
    val imageIcon: Painter?,
    val contentDescription: String,
    val modifier: Modifier
)