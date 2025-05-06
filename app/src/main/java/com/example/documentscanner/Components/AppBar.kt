package com.example.documentscanner.Components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentscanner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Appbar(){
    TopAppBar(title = { Text(text = "  Document Scanner", fontSize = 16.sp, fontWeight = FontWeight.W400, color = Color.White)},
        navigationIcon = { Icon(painter = painterResource(id = R.drawable.free_pdf), tint = Color.Unspecified, contentDescription = null,
            modifier = Modifier.size(30.dp))},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        ))
}