package com.example.documentscanner.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.documentscanner.Model.ViewImagesState
import com.example.documentscanner.ViewModel.MainViewModel

@Composable
fun SecondScreen(viewModel: MainViewModel,navController: NavController){

    //here we collect our data  through Stateflow  that viewmodel and Ui sync with each other
    val viewState : ViewImagesState by viewModel.State.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        LazyColumn {
            //here we created LazyColumn which shows the captured Images
            itemsIndexed(viewState.SelectedImages) {index,pictures ->
                Image(
                    bitmap = pictures, contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)){
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                ), shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Convert In Word")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                ), shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Convert In Pdf")
            }
        }
    }
}