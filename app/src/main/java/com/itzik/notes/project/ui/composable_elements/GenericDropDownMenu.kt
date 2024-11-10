package com.itzik.notes.project.ui.composable_elements

import android.annotation.SuppressLint
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.itzik.notes.project.viewmodels.NoteViewModel
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

    val genderList: List<String> = listOf("Male", "Female", "Other")

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
                Text(
                    text = it,
                    color = Color.Black
                    )
            }
        }
    }
}
