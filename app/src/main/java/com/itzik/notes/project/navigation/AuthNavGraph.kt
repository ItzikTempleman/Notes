package com.itzik.notes.project.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itzik.notes.project.screens.login_screens.LoginScreen

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController){
    navigation(
        route =  Graph.LOGIN_AND_REGISTRATION,
        startDestination = AuthScreen.Login.route
    ){
        composable(
            route = AuthScreen.Login.route
        ){
             LoginScreen(
                login = {
                    navHostController.popBackStack()
                    navHostController.navigate(Graph.NOTE_GRAPH)
                } ,
                 signUp = {
                     navHostController.navigate(AuthScreen.SignUp.route)
                 },
                 forgotPassword = {
                     navHostController.navigate(AuthScreen.Forgot.route)
                 }
             )
        }
    }
}




sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
}