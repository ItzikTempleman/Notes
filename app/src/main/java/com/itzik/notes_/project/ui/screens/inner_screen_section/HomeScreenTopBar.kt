package com.itzik.notes_.project.ui.screens.inner_screen_section

import android.widget.RadioGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.rounded.ImageSearch
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.notes_.R
import com.itzik.notes_.project.model.User
import com.itzik.notes_.project.ui.composable_elements.GenericFloatingActionButton
import com.itzik.notes_.project.ui.composable_elements.GenericIconButton

@Composable
fun HomeScreenTopBar(
    modifier: Modifier,
    onOpenWallpaperSearch: () -> Unit,
    onSortButtonClick: () -> Unit,
    onSelectView: (Boolean) -> Unit,
    isViewGrid: MutableState<Boolean>,
    user: User?,
    onChecked: (Boolean) -> Unit
) {
    var isChecked by remember {
        mutableStateOf(false)
    }
    var selectedOption by remember { mutableStateOf("Local") }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        val (searchWallpaperIcon, guestAccountText, localRadioBtn,onlineRadioBtn,sortNotesIcon, noteViewTypeIcon) = createRefs()

        GenericIconButton(
            modifier = Modifier
                .constrainAs(searchWallpaperIcon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .size(50.dp)
                .padding(vertical = 12.dp),
            onClick = onOpenWallpaperSearch,
            colorNumber = 4,
            imageVector = Icons.Rounded.ImageSearch
        )

        Text(
            modifier = Modifier.constrainAs(guestAccountText) {
                start.linkTo(searchWallpaperIcon.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            color = colorResource(R.color.deep_ocean_blue),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            text = if (user?.userName == "Guest") stringResource(R.string.guest_account) else ""
        )

        if (user?.userName != stringResource(R.string.guest_account)) {


            RadioButton(
                modifier = Modifier.constrainAs(localRadioBtn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(searchWallpaperIcon.end)
                },
                selected = selectedOption == "Local",
                onClick = {
                    selectedOption = "Local"
                    isChecked = false
                    onChecked(false)
                }
            )

            RadioButton(
                modifier = Modifier.constrainAs(onlineRadioBtn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(localRadioBtn.end)
                },
                selected = selectedOption == "Online",
                onClick = {
                    selectedOption = "Online"
                    isChecked = true
                    onChecked(true)
                }
            )
        }

        GenericIconButton(
            modifier = Modifier
                .rotate(90f)
                .constrainAs(sortNotesIcon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(noteViewTypeIcon.start)
                }
                .size(50.dp)
                .padding(vertical = 12.dp),

            onClick = onSortButtonClick,
            colorNumber = 4,
            imageVector = Icons.AutoMirrored.Default.CompareArrows
        )

        GenericFloatingActionButton(
            modifier = Modifier
                .constrainAs(noteViewTypeIcon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .padding(8.dp)
                .size(35.dp),
            onClick = {
                onSelectView(isViewGrid.value)
            },
            imageVector = if (!isViewGrid.value) Icons.Default.List else Icons.Default.GridView,
            containerColor = Color.White,
            iconTint = if (!isViewGrid.value) colorResource(id = R.color.deep_ocean_blue) else colorResource(
                id = R.color.muted_yellow
            ),
        )
    }
}