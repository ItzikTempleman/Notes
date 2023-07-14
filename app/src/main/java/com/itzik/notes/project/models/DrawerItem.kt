package com.itzik.notes.project.models

import android.graphics.drawable.Icon
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val contentDescription:String
)