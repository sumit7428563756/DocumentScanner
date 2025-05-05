package com.example.documentscanner.Components

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.remember
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions


fun recognizeTextFromImage(context: Context, imageUri: Uri, onTextExtracted: (String) -> Unit) {
    try {
        val image = InputImage.fromFilePath(context, imageUri)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                onTextExtracted(visionText.text)
            }
            .addOnFailureListener { e ->
                onTextExtracted("Text recognition failed: ${e.localizedMessage}")
            }
    } catch (e: Exception) {
        onTextExtracted("Error reading image: ${e.localizedMessage}")
    }
}