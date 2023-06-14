package com.itzik.notes.project.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itzik.notes.project.navigation.MainGraph.AUTH
import com.itzik.notes.project.navigation.MainGraph.HOME
import com.itzik.notes.project.screens.login_screens.LoginScreen


fun NavGraphBuilder.authNavGraph(navHostController: NavHostController){
    navigation(
        route =  AUTH,
        startDestination = AuthScreen.Login.route
    ){
        composable(
            route = AuthScreen.Login.route
        ){
             LoginScreen(
                login = {
                    navHostController.popBackStack()
                    navHostController.navigate(HOME)
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
    object Login : AuthScreen(route = "login")
    object SignUp : AuthScreen(route = "signup")
    object Forgot : AuthScreen(route = "reset")
}