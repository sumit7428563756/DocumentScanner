package com.example.documentscanner.ViewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ImagePicViewModoel : ViewModel() {

   var imageUri by mutableStateOf<Uri?>(null)


   var capturedImageUri by mutableStateOf<Uri?>(null)


     var recognizedText by mutableStateOf("")


    fun SetImageUri(uri: Uri?) {
        imageUri = uri
    }

    fun SetCapturedImageUri(uri: Uri?) {
        capturedImageUri = uri
    }

    fun SetRecognizedText(text: String) {
        recognizedText = text
    }
}