package com.itzik.notes.project.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.itzik.notes.project.models.MenuItem


@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    onClick: (MenuItem) -> Unit,
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(item)
                    }
                    .padding(top = 70.dp, start = 16.dp)
            ) {
                item.vectorIcon?.let { Icon(imageVector = it, contentDescription = null) }
                item.imageIcon?.let { Icon(painter = it, contentDescription = null) }
                Text(text = item.title, modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}


fun customShape() = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val roundRect = RoundRect(
            15f,
            150f,
            550f,
            280f,
            CornerRadius(20f),
            CornerRadius(20f),
            CornerRadius(20f),
            CornerRadius(20f)
        )
        return Outline.Rounded(roundRect)

    }
}