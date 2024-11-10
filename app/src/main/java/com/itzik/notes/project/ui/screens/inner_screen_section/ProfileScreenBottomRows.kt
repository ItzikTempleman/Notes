package com.itzik.notes.project.ui.screens.inner_screen_section


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


sealed class ProfileScreenBottomRows(
    val itemName: String,
    val itemIcon: ImageVector,
    val tint: Color,
) {
    data object DeletedNotes : ProfileScreenBottomRows(
        itemName = "Trash",
        itemIcon = Icons.Default.DeleteForever,
        tint = Color.Black
    )

    data object LogOut : ProfileScreenBottomRows(
        itemName = "Log Out",
        itemIcon = Icons.Default.PowerSettingsNew,
        tint = Color.Red
    )
}

@Composable
fun BottomRowProfileScreenItems(
    item: ProfileScreenBottomRows,
    modifier: Modifier,
    onItemSelected: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(8.dp)
                .clickable {
                    onItemSelected()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                imageVector = item.itemIcon,
                contentDescription = null,
                tint = item.tint
            )
            Text(modifier = Modifier.padding(start = 8.dp),
                text = item.itemName, fontSize = 20.sp, color = item.tint
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun BottomOptionsProfileScreen(
    modifier: Modifier,
    onItemSelected: (BottomScreenOption) -> Unit,
) {
    val bottomOptionsRow = listOf(
        ProfileScreenBottomRows.DeletedNotes,
        ProfileScreenBottomRows.LogOut,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        bottomOptionsRow.forEach {
            BottomRowProfileScreenItems(
                item = it,
                modifier = Modifier,
                onItemSelected = {
                    when (it) {
                        is ProfileScreenBottomRows.DeletedNotes -> onItemSelected(BottomScreenOption.TRASH)
                        is ProfileScreenBottomRows.LogOut -> onItemSelected(BottomScreenOption.LOG_OUT)
                    }
                }
            )
        }
    }
}
enum class BottomScreenOption {
    TRASH, LOG_OUT
}