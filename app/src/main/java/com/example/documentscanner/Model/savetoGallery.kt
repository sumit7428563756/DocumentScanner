package com.example.documentscanner.Model

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore


fun saveImageToGallery(context: Context, uri: Uri) {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return
    val filename = "IMG_${System.currentTimeMillis()}.jpg"

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
    }

    val imageUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    imageUri?.let {
        context.contentResolver.openOutputStream(it).use { outputStream ->
            inputStream.copyTo(outputStream!!)
        }
    }
}