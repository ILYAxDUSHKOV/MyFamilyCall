package com.example.myfamilycall2

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.ContactScreen.route //или вручную написать значение route ("contact_screen")
    ){
        composable(
            route = Screen.ContactScreen.route
        ){
            MyHomeScreenn(navHostController = navController)
        }
        composable(
            route = Screen.AlertScreen.route
        ){
            SecondScreen(navHostController = navController)
        }
    }
}