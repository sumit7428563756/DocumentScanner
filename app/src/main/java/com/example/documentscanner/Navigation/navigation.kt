package com.example.documentscanner.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.documentscanner.View.Home
import com.example.documentscanner.View.ImagePicScreen
import com.example.documentscanner.View.ImagePickerScreen
import com.example.documentscanner.View.Screen
import com.example.documentscanner.ViewModel.ImagePicViewModoel

@Composable
fun navigate(viewModel : ImagePicViewModoel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = start ) {
        composable(start){
            Screen(navController = navController)
        }
        composable(home){
            Home(navController = navController)
        }
        composable(gallery){
           ImagePicScreen(viewModel = viewModel,navController = navController)
        }
        composable(camera){
            ImagePickerScreen(viewModel = viewModel,navController = navController)
        }

    }
}

const val start = "Start"
const val home = "Home"
const val gallery = "Gallery"
const val camera = "Camera"
