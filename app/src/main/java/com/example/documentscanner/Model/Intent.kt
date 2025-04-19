package com.example.documentscanner.Model

import android.content.Context
import android.net.Uri


//Here We created a Sealed Class Which perfroms Via Ui
sealed class Intent {

          //On below Line We created A class Which Holds the When Permission Granted
     data class OnpermissionGranted(val context : Context):Intent()

    //On below Line We created A object Which Holds An data Object Which Perform Error,progress Etc When Permission Denied
    data object OnPermissionDenied : Intent()

    //here We create another class which holds data when Image saved
    data class onImageSaved(val context: Context):Intent()

    //here we create object when Image saved Cancelled
    data object OnImageCancel:Intent()

    //here we create data class which perform to show Pictures when picking images done
    data class OnFinishPicking(val context: Context,val images : List<Uri>):Intent()
}