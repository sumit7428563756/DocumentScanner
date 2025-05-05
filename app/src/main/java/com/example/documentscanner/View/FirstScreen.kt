package com.example.documentscanner.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.example.documentscanner.Navigation.home
import com.example.documentscanner.R


@Composable
fun Screen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = R.drawable.background),
            contentScale = ContentScale.FillBounds
        )
        .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animaion))
        LottieAnimation(composition = composition, modifier = Modifier.aspectRatio(1f), iterations = LottieConstants.IterateForever)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Convert Any Image", textAlign = TextAlign.Center, style = TextStyle(
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Into", textAlign = TextAlign.Center, style = TextStyle(
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            color = Yellow
        )
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp), horizontalArrangement = Arrangement.Center, // 👈 This centers the items
            verticalAlignment = Alignment.CenterVertically // Optional: Align items vertically
          ) {
            Image(
                painter = painterResource(id = R.drawable._296673_microsoft_office_office365_word_icon),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "OR",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                color = White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable._74684_pdf_document_extension_file_format_icon),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        IconButton(onClick = {navController.navigate(home) }, modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
            .height(50.dp)
            .background(color = Yellow, shape = RoundedCornerShape(40.dp)), colors = IconButtonDefaults.iconButtonColors(
            contentColor = Black,
        )) {
            Text(text = "Get Start", fontSize = 40.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

//
//@Composable
//@Preview(showBackground = true)
//fun preview(){
//    Screen()
//}


