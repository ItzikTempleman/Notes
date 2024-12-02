package com.itzik.notes_.project.ui.composable_elements

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import com.itzik.notes_.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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

    val sortOptions: List<String> = listOf("Sort by date modified", "Sort alphabetically")

    DropdownMenu(
        modifier = modifier,
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
}

@Composable
fun GenderDropDownMenu(
    updatedList: (String) -> Unit,
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    isExpanded: Boolean,
    onDismissRequest: () -> Unit
) {

    val genderList: List<String> = listOf(stringResource(R.string.male), stringResource(R.string.female), stringResource(R.string.other))

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
                        text = it,
                        color = Color.Black
                    )
                    if (it.toString()!= stringResource(R.string.other)) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
