package com.itzik.notes.project.ui.navigation


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.itzik.notes.project.ui.registrations.LoginScreen
import com.itzik.notes.project.ui.registrations.RegistrationScreen
import com.itzik.notes.project.ui.registrations.SplashScreen
import com.itzik.notes.project.ui.navigation.Graph.AUTHENTICATION
import com.itzik.notes.project.ui.navigation.Graph.HOME
import com.itzik.notes.project.ui.navigation.Graph.ROOT
import com.itzik.notes.project.viewmodels.NoteViewModel
import com.itzik.notes.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope


@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)

@Composable
fun RootNavHost(
    userId: String,
    noteViewModel: NoteViewModel,
    rootNavController: NavHostController,
    userViewModel: UserViewModel,
    coroutineScope: CoroutineScope,
) {
        NavHost(
            startDestination = ROOT,
            navController = rootNavController
        ) {
            navigation(
                startDestination = Screen.Splash.route,
                route = ROOT
            ) {
                composable(route = Screen.Splash.route) {
                    SplashScreen(
                        rootNavController = rootNavController,
                        userViewModel = userViewModel,
                    )
                }
            }

            navigation(
                startDestination = Screen.Login.route,
                route = AUTHENTICATION
            ) {
                composable(route = Screen.Login.route) {
                    LoginScreen(
                        rootNavController = rootNavController,
                        userViewModel = userViewModel,
                        coroutineScope = coroutineScope,

                        )
                }

                composable(route = Screen.Registration.route) {
                    RegistrationScreen(
                        rootNavController = rootNavController,
                        userViewModel = userViewModel,
                        coroutineScope = coroutineScope
                    )
                }

                composable(route = Screen.ResetPassword.route) {
//                ResetPasswordScreen(
//                    rootNavController = rootNavController,
//                    userViewModel = userViewModel,
//                    coroutineScope = coroutineScope
//                )
//                ResetPasswordFromEmailScreen(
//                    rootNavController = rootNavController,
//                    userViewModel = userViewModel,
//                    coroutineScope = coroutineScope
//
//                )
                }
            }

            navigation(
                startDestination = Screen.Home.route,
                route = HOME
            ) {
                composable(route = Screen.Home.route) {
                    val bottomBarNavController = rememberNavController()
                    BottomBarNavHost(
                        bottomBarNavController = bottomBarNavController,
                        noteViewModel = noteViewModel,
                        rootNavController = rootNavController,
                        userViewModel = userViewModel,
                        coroutineScope = coroutineScope,
                        userId = userId,
                    )
                }
            }
        }
    }







