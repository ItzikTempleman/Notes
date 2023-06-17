package com.itzik.notes.project.screens.splash_screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Note
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import com.itzik.notes.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.itzik.notes.project.navigation.AuthGraph
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val doesUserExist = mutableStateOf(false)
    val alphaAim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2500
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2500)
        navHostController.popBackStack()
        if (doesUserExist.value)
            navHostController.navigate(HomeGraph.Notes.route)
        else navHostController.navigate(AuthGraph.Login.route)
    }
    SplashScreen(alpha = alphaAim.value)
}


@Composable
fun SplashScreen(alpha: Float) {
    Column(

        modifier = Modifier
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier,
            color = colorResource(id = R.color.yellow),
            fontFamily = FontFamily.Cursive,
            fontSize = 100.sp
        )

        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha = alpha),
            imageVector = Icons.Default.Note,
            contentDescription = stringResource(id = R.string.app_name),
            tint = (if (isSystemInDarkTheme()) Color.White else  colorResource(id = R.color.turquoise))
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