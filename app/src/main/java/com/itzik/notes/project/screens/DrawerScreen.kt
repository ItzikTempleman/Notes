package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.itzik.notes.project.navigation_drawer.AppBar
import com.itzik.notes.project.navigation_drawer.DrawerBody
import com.itzik.notes.project.navigation_drawer.DrawerHeader
import com.itzik.notes.project.navigation_drawer.MenuItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DrawerScreen(coroutineScope: CoroutineScope, modifier: Modifier) {

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerContent = {
            DrawerHeader()
            DrawerBody(itemList =
            listOf(
                MenuItem(
                    id = "Deleted notes",
                    title = "Deleted notes",
                    contentDescription = "",
                    icon = Icons.Default.Recycling
                )
            ), onClick = {
                moveToDeletedNotesScreen()
            })
        }
    ) {

    }
}
