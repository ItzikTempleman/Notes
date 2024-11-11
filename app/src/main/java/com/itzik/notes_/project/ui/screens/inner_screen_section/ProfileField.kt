package com.itzik.notes_.project.ui.screens.inner_screen_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class ProfileField(val icon: ImageVector) {
    data object Email : ProfileField(Icons.Outlined.Email)
    data object PhoneNumber : ProfileField( Icons.Outlined.Phone)
    data object Gender : ProfileField(Icons.Outlined.Male)
    data object DateOfBirth : ProfileField( Icons.Outlined.CalendarToday)
}



@Composable
fun ProfileFieldRow(profileField: ProfileField, value: String, customIcon: ImageVector? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val icon = customIcon ?: profileField.icon
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value)
    }
}