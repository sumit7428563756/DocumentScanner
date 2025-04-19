package com.example.documentscanner.Model

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

//Here We Created A data Class Which Holds The State of MainScreen

data class ViewImagesState(
//holds the Url Which taken From The Camera
    val fileurl : Uri? = null,
    //holds the Selected pictures From gallery.
    val SelectedImages : List<ImageBitmap> = emptyList(),
)
