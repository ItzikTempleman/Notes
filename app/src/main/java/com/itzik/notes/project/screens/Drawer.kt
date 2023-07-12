package com.itzik.notes.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
fun NavigationDrawerScreen(
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
) {

        Scaffold(
            modifier = modifier.fillMaxSize(),
//            scaffoldState = scaffoldState,
//            topBar = {
//                TopAppBar(
//                    title = {
//                    },
//
//                    backgroundColor = colorResource(id = R.color.white),
//                    navigationIcon = {
//                        Icon(imageVector = Icons.Default.Menu, contentDescription = "",
//                            modifier = Modifier
//                                .clickable {
//                                    coroutineScope.launch {
//                                        scaffoldState.drawerState.open()
//                                    }
//                                }
//                                .padding(start = 8.dp)
//                        )
//                    }
//                )
//            },
//
//            drawerContent = {
//                Column(
//                    Modifier
//                        .fillMaxWidth()
//                        .background(colorResource(id = R.color.light_teal))
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "",
//                        modifier = Modifier
//                            .clickable {
//                                coroutineScope.launch {
//                                    scaffoldState.drawerState.close()
//                                }
//                            }
//                            .padding(start = 8.dp, top = 20.dp, bottom = 64.dp)
//                    )
//
//                    Icon(
//                        imageVector = Icons.Default.Recycling,
//                        contentDescription = "",
//                        modifier = Modifier
//                            .clickable {
//                                moveToDeletedNotesScreen()
//                            }
//                            .padding(start = 8.dp, bottom = 16.dp)
//                    )
//
//
//                }
//
//            }
        ) {}
    }


