package com.example.documentscanner.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.documentscanner.View.FirstScreen
import com.example.documentscanner.View.SecondScreen
import com.example.documentscanner.ViewModel.MainViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
//here we create NavigationScreen

fun navigate( ViewModel : MainViewModel){
    //Here we created Navigation by Using NavController Dependency
    val NavController = rememberNavController()
    NavHost(navController = NavController, startDestination = Start) {
        //here we call our firstScreen to navigate
        composable(Start){
            FirstScreen(viewModel = ViewModel, navController = NavController )
        }
        //here we call our Second Screen to navigate
        composable(Second){
            SecondScreen(viewModel = ViewModel, navController = NavController )
        }
    }
}

const val Start = "FirstScreen"
const val Second = "SecondScreen"
