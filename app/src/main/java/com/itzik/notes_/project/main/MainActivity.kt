package com.itzik.notes_.project.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itzik.notes_.project.ui.navigation.RootNavHost
import com.itzik.notes_.project.viewmodels.NoteViewModel
import com.itzik.notes_.project.viewmodels.UserViewModel
import com.itzik.notes_.ui.theme.NotesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var userViewModel: UserViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val coroutineScope: CoroutineScope = rememberCoroutineScope()
            val navController: NavHostController = rememberNavController()
            userViewModel = viewModel()
            noteViewModel = viewModel()

            val user by userViewModel.publicLoggedInUsersList.collectAsState()
            val userId = user.firstOrNull()?.userId ?: ""

            LaunchedEffect(userId) {
                if (userId.isNotEmpty()) {
                    noteViewModel.fetchNotesForUser(userId)
                }
            }
            NotesTheme {
                RootNavHost(
                    userId = userId,
                    noteViewModel = noteViewModel,
                    userViewModel = userViewModel,
                    coroutineScope = coroutineScope,
                    rootNavController = navController
                )
            }
        }
    }
}


