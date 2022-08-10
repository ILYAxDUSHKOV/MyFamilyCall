package com.example.myfamilycall2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myfamilycall2.ui.theme.MyFamilyCall2Theme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFamilyCall2Theme() {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}