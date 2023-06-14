package com.itzik.notes.project.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itzik.notes.project.navigation.AppGraph.AUTH
import com.itzik.notes.project.navigation.AppGraph.NOTES_HOME
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
                    navHostController.navigate(NOTES_HOME)
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
