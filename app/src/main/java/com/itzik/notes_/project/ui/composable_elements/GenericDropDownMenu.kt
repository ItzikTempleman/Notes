package com.itzik.notes_.project.ui.composable_elements

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material.icons.filled.Woman
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import com.itzik.notes_.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.itzik.notes_.project.model.Gender
import com.itzik.notes_.project.ui.composable_elements.GenderDropDownItem.Female
import com.itzik.notes_.project.ui.composable_elements.GenderDropDownItem.Male
import com.itzik.notes_.project.ui.composable_elements.GenderDropDownItem.Other
import com.itzik.notes_.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SortDropDownMenu(
    updatedSortedList: (String) -> Unit,
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    noteViewModel: NoteViewModel,
    isExpanded: Boolean,
    onDismissRequest: () -> Unit
) {

    val sortOptions: List<String> = listOf(stringResource(R.string.sort_date), stringResource(R.string.sort_alphabetically))

    Box(
        modifier = modifier,
    ){
    DropdownMenu(
        modifier = Modifier,
        expanded = isExpanded,
        onDismissRequest = onDismissRequest
    ) {
        sortOptions.forEach {
            DropdownMenuItem(
                onClick = {
                    coroutineScope.launch {
                        noteViewModel.getSortedNotes(it)
                        updatedSortedList(it)
                    }
                    onDismissRequest()
                }
            ) {
                Text(text = it)
            }
        }
    }
}}


sealed class GenderDropDownItem(val gender: Gender, val icon: ImageVector) {
    data object Male : GenderDropDownItem(Gender.MALE, Icons.Default.Male)
    data object Female : GenderDropDownItem(Gender.FEMALE, Icons.Default.Female)
    data object Other : GenderDropDownItem(Gender.OTHER, Icons.Default.Transgender)
}


fun getGenderIcon(gender: Gender): ImageVector {
    return when(gender){
        Gender.MALE -> Icons.Default.Man
        Gender.FEMALE -> Icons.Default.Woman
        Gender.OTHER -> Icons.Default.Transgender
    }
}

@Composable
fun GenderDropDownMenu(
    updatedList: (GenderDropDownItem) -> Unit,
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    isExpanded: Boolean,
    onDismissRequest: () -> Unit
) {

    val genderList = listOf(Male, Female, Other)

    DropdownMenu(
        modifier = modifier,
        expanded = isExpanded,
        onDismissRequest = onDismissRequest
    ) {
        genderList.forEach {
            DropdownMenuItem(
                onClick = {
                    coroutineScope.launch {
                        updatedList(it)
                    }
                    onDismissRequest()
                }
            ) {
                Column {
                    Text(
                        text = it.gender.toString(),
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                    if (it.toString() != stringResource(R.string.other)) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
