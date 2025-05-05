package com.example.documentscanner.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.documentscanner.Components.Appbar
import com.example.documentscanner.Navigation.camera
import com.example.documentscanner.Navigation.gallery
import com.example.documentscanner.R

@Composable
fun Home(navController: NavController){
     Column(modifier = Modifier
         .fillMaxSize()
         .paint(
             painter = painterResource(id = R.drawable.background),
             contentScale = ContentScale.FillBounds
         ),
         horizontalAlignment = Alignment.CenterHorizontally) {
         Appbar()
         val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation))
         LottieAnimation(
             composition = composition, modifier = Modifier
                 .aspectRatio(1f)
                 .padding(top = 40.dp), iterations = LottieConstants.IterateForever
         )
         Spacer(modifier = Modifier.height(10.dp))
         Text(
             text = "  Convert With",
             fontSize = 40.sp,
             fontWeight = FontWeight.W800,
             color = Color.White,
             textAlign = TextAlign.Center
         )
         Spacer(modifier = Modifier.height(10.dp))
             Row(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(horizontal = 20.dp),
                 horizontalArrangement = Arrangement.Center, // 👈 Center items in the row
                 verticalAlignment = Alignment.CenterVertically
             ) {
                 Card(
                     modifier = Modifier
                         .height(150.dp)
                         .width(150.dp)
                         .clickable { navController.navigate(gallery) },
                     elevation = CardDefaults.cardElevation(8.dp)
                 ) {
                     Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                         Image(
                             painter = painterResource(id = R.drawable._004736_image_photo_picture_gallery_icon),
                             contentDescription = null,modifier = Modifier.size(100.dp)
                         )
                     }
                 }
                 Spacer(modifier = Modifier.width(30.dp))
                 Card(
                     modifier = Modifier
                         .height(150.dp)
                         .width(150.dp)
                         .clickable { navController.navigate(camera) },
                     elevation = CardDefaults.cardElevation(8.dp)
                 ) {
                     Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                         Image(
                             painter = painterResource(id = R.drawable._85649_camera_icon),
                             contentDescription = null, modifier = Modifier.size(100.dp)
                         )
                     }
                 }
             }
                 Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),  horizontalArrangement = Arrangement.Center, // 👈 Center items in the row
                     verticalAlignment = Alignment.CenterVertically) {
                     Spacer(modifier = Modifier.height(20.dp))
                         Text(text = "Select From Gallery", textAlign = TextAlign.Center, fontWeight = FontWeight.W800, fontSize = 14.sp, modifier = Modifier.padding(end = 40.dp))
                     Spacer(modifier = Modifier.width(40.dp))
                        Text(text = "Open Camera", textAlign = TextAlign.Center, fontWeight = FontWeight.W800, fontSize = 14.sp, modifier = Modifier.padding(end = 40.dp))
                 }

             }
         }

