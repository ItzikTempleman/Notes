package com.itzik.notes_.project.ui.screens.inner_screen_section

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.itzik.notes_.R
@Composable
fun ProfileImage(
    isGuestAccount: Boolean,
    imageUri: String?,
    onImageSelected: () -> Unit,
    imageBoxModifier: Modifier,
    cancelIconModifier:Modifier,
    onRemoveImage: () -> Unit,
) {
    Box(
        modifier = imageBoxModifier
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .size(130.dp)
            .clip(CircleShape)
            .background(Color.White).border(
                border = BorderStroke(0.8.dp, colorResource(R.color.deep_ocean_blue)),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (!imageUri.isNullOrEmpty()) {
            Image(
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                tint = colorResource(R.color.deep_ocean_blue),
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
            )
        }
        if (!isGuestAccount) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-8).dp, y = (-6).dp)
                    .zIndex(2f),
                onClick = onImageSelected
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    tint = colorResource(R.color.deep_ocean_blue),
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
    if (!isGuestAccount) {
        IconButton(
            modifier = cancelIconModifier
                .offset(x = (-12).dp, y = (-30).dp)
                .size(36.dp),
            onClick = onRemoveImage
        ) {
            Icon(
                tint = colorResource(R.color.deep_ocean_blue),
                imageVector = Icons.Default.Cancel,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


