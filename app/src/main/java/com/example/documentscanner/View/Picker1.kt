package com.example.documentscanner.View

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.documentscanner.Components.AppBar
import com.example.documentscanner.Components.Appbar
import com.example.documentscanner.Components.recognizeTextFromImage
import com.example.documentscanner.Components.saveTextAsPdf
import com.example.documentscanner.Model.saveImageToGallery
import com.example.documentscanner.R
import com.example.documentscanner.ViewModel.ImagePicViewModoel
import createDocxFromText


@Composable
fun ImagePicScreen(viewModel : ImagePicViewModoel,navController: NavController) {

    val context = LocalContext.current

    val permissionState = remember { mutableStateOf(false) }

    val imageuri = viewModel.imageUri
    val capturedImageuri = viewModel.capturedImageUri
    val recognizedtext = viewModel.recognizedText

    val cameraPermission = Manifest.permission.CAMERA

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionState.value = isGranted
            if (!isGranted) {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    val launcherGallery =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.SetImageUri(it)
                saveImageToGallery(context, it)
                recognizeTextFromImage(context, it) {
                    viewModel.SetRecognizedText(it)
                }
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
         horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar(navController = navController)
        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.height(50.dp))
            Button(onClick = {
                val granted = ContextCompat.checkSelfPermission(
                    context,
                    cameraPermission
                ) == PackageManager.PERMISSION_GRANTED
                if (granted) {
                    launcherGallery.launch("image/*")
                } else {
                    launcher.launch(cameraPermission)
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ), shape = RoundedCornerShape(8.dp) ) {
                Text("Pick from Gallery")
            }
            Spacer(modifier = Modifier.height(10.dp))
            imageuri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (recognizedtext.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = recognizedtext,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Convert With", textAlign = TextAlign.Center, style = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.W600,
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.Center, // 👈 This centers the items
                        verticalAlignment = Alignment.CenterVertically // Optional: Align items vertically
                    ) {
                        Box(modifier = Modifier
                            .height(50.dp)
                            .clickable {
                                val uri = createDocxFromText(context, recognizedtext)
                                if (uri != null) {
                                    Toast
                                        .makeText(
                                            context,
                                            "Saved to Documents",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                }
                            }
                            .width(50.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable._296673_microsoft_office_office365_word_icon),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "OR",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .clickable { saveTextAsPdf(context, recognizedtext) }
                            .width(50.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable._74684_pdf_document_extension_file_format_icon),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}



