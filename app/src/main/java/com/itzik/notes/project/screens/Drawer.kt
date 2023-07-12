package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.itzik.notes.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DrawerScreen(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    Scaffold(
        Modifier,
        scaffoldState = scaffoldState,
        drawerElevation = 8.dp,
        topBar = {
            TopAppBar(
                title = {},
                backgroundColor = colorResource(id = R.color.white),
                contentColor = colorResource(id = R.color.black),
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Toggle drawer")
                    }
                }
            )
        },
        drawerContent = {
            Icon(imageVector = Icons.Default.Recycling, contentDescription = "", modifier = Modifier.clickable {
                moveToDeletedNotesScreen()
            }.padding(64.dp)
            )
        }
    ) {}
}

