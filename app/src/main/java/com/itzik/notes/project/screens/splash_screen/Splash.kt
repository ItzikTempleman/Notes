package com.itzik.notes.project.screens.splash_screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Note
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.itzik.notes.project.navigation.AppGraph
import com.itzik.notes.project.viewmodels.NoteViewModel
import com.itzik.notes.theme.Purple700
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val doesUserExist = mutableStateOf(true)
    val alphaAim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        navHostController.popBackStack()
        if (doesUserExist.value)
            navHostController.navigate(AppGraph.Notes.route)
        else navHostController.navigate(AppGraph.Login.route)
    }
    SplashScreen(alpha = alphaAim.value)
}


@Composable
fun SplashScreen(alpha: Float) {
    Box(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) Color.Black else Purple700)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha = alpha),
            imageVector = Icons.Default.Note,
            contentDescription = "Logo icon",
            tint = Color.White
        )

    }
}

@Composable
@Preview
fun SplashScreenPreView() {
    SplashScreen(alpha = 1f)
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenDarkPreView() {
    SplashScreen(alpha = 1f)
}