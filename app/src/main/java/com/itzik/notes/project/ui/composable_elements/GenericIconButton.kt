package com.itzik.notes.project.ui.composable_elements


import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.itzik.notes.R

@Composable
fun GenericIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    iconSize: Modifier = Modifier.size(32.dp),
    colorNumber: Int,
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onClick()
        }
    ) {
        Icon(
            modifier = iconSize,
            imageVector = imageVector,
            contentDescription = null,
            tint = when (colorNumber) {
                0 -> colorResource(id = R.color.olive_green)
                1 -> colorResource(id = R.color.deep_ocean_blue)
                2 -> colorResource(id = R.color.sunset_orange)
                3 -> colorResource(id = R.color.desaturated_dark_red)
                else -> {
                    Color.DarkGray
                }
            }
        )
    }
}


@Composable
fun GenericFloatingActionButton(
    modifier: Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    containerColor: Color,
    iconTint: Color,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = containerColor,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 16.dp
        )
    ) {
        Icon(
            modifier = Modifier,
            imageVector = imageVector,
            contentDescription = null,
            tint = iconTint
        )
    }
}