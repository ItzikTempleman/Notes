package com.itzik.notes_.project.ui.registrations


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.itzik.notes_.R

import com.itzik.notes_.project.ui.navigation.Graph.AUTHENTICATION
import com.itzik.notes_.project.ui.navigation.Graph.HOME
import com.itzik.notes_.project.utils.gradientBrush
import com.itzik.notes_.project.viewmodels.UserViewModel
import kotlinx.coroutines.delay

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SplashScreen(
    rootNavController: NavHostController,
    userViewModel: UserViewModel
) {
    val loggedInUsers by userViewModel.publicLoggedInUsersList.collectAsState()

    LaunchedEffect(loggedInUsers) {
        delay(1500)
        if (loggedInUsers.isNotEmpty() && loggedInUsers.first().isLoggedIn) {
            rootNavController.popBackStack()
            rootNavController.navigate(HOME)
        } else {
            rootNavController.popBackStack()
            rootNavController.navigate(AUTHENTICATION)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.android_material),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        CircularProgressIndicator(
            color = Color.Gray
        )
    }
}