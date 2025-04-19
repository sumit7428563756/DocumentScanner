package com.example.documentscanner.View

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController

import com.example.documentscanner.Model.Intent
import com.example.documentscanner.Model.ViewImagesState
import com.example.documentscanner.Navigation.Second
import com.example.documentscanner.ViewModel.MainViewModel
import java.util.Objects


@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun FirstScreen(viewModel: MainViewModel,navController: NavController){

 //Context for Activity
    val context = LocalContext.current

    //here we collect our data  through Stateflow  that viewmodel and Ui sync with each other
    val viewState : ViewImagesState by viewModel.State.collectAsState()

    //Here We create launcher for Select images from Gallery
   val pickImage = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia()) {url ->
       viewModel.OnRecieve(Intent.OnFinishPicking(context,url)) }

    //Here we create a launcher for take Pictures
      val CameraLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {IsImageSaved ->
          //here we Create if Statement to saved Taken Image
          if(IsImageSaved){
              viewModel.OnRecieve(Intent.onImageSaved(context))
          }
          //here we create else Statement to handles Errors
          else{
              viewModel.OnRecieve(Intent.OnImageCancel)
          }
      }

    //here we create launcher for camera Permissions
    val permission = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { permissionGranted ->
        if(permissionGranted){
            viewModel.OnRecieve(Intent.OnpermissionGranted(context))
        }else{
            viewModel.OnRecieve(Intent.OnPermissionDenied)
        }
    }

 // it indicates that the camera is Launched only once when the url changes in temp file
    LaunchedEffect(key1 = viewState.fileurl) {
        viewState.fileurl?.let {
            CameraLauncher.launch(it)
        }
    }

    Column(modifier = Modifier
        .background(Color.Black)
        .fillMaxSize()
        .padding(20.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(onClick = {
            //here we apply pickImage launcher
          val media = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            pickImage.launch(media)
            //here After pickup image it transfer to SecondScreen
                         navController.navigate(Second)},
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            ), modifier = Modifier.padding(5.dp), shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Select From File")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            //here we apply permission for camera launch
            permission.launch(Manifest.permission.CAMERA)
            //here After pickup image it transfer to SecondScreen
            navController.navigate(Second)
        },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.White), modifier = Modifier.padding(5.dp), shape = RoundedCornerShape(8.dp)) {
            Text(text = "Choose From Camera")
        }
    }
}