package com.example.documentscanner.ViewModel


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.documentscanner.Model.Intent
import com.example.documentscanner.Model.ViewImagesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext
import com.example.documentscanner.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
//Here We Created A ViewModel Which Performs data and Secure the Activity
class MainViewModel @Inject constructor(private val coroutineContext: CoroutineContext):ViewModel() {

    //here we create A private state which gets the data Privately in the mutableStateFlow
    private val _State: MutableStateFlow<ViewImagesState> = MutableStateFlow(ViewImagesState())

    //here we create a public val which holds and show the data in the composable Screen
    val State: StateFlow<ViewImagesState>
        get() = _State

    //here we create functions Which performs our intents In composable Screen
    @RequiresApi(Build.VERSION_CODES.P)
    fun OnRecieve(intent: Intent) = viewModelScope.launch(coroutineContext) {
        when (intent) {

            is Intent.OnFinishPicking -> {
                if (intent.images.isNotEmpty()) {

                    val newImages = mutableListOf<ImageBitmap>()
                    for (eachImageUrl in intent.images) {
                        val inputStream =
                            intent.context.contentResolver.openInputStream(eachImageUrl)
                        val bytes = inputStream?.readBytes()
                        inputStream?.close()

                        if (bytes != null) {
                            val bitmapOptions = BitmapFactory.Options()
                            bitmapOptions.inMutable = true
                            val bitmap: Bitmap =
                                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bitmapOptions)
                            newImages.add(bitmap.asImageBitmap())
                        } else {
                            println("The image that was picked could not be read from the device at this url: $eachImageUrl")
                        }
                    }
                    val currentViewState = _State.value
                    val newCopy = currentViewState.copy(
                        SelectedImages = (currentViewState.SelectedImages),
                        fileurl = null
                    )
                    _State.value = newCopy
                } else {

                }

            }

            Intent.OnImageCancel -> {
                _State.value = _State.value.copy(fileurl = null)

            }

            Intent.OnPermissionDenied -> {
                println("User Did not grant permission to use the CAMERA")

            }

            is Intent.OnpermissionGranted -> {

                //here we create empty cache file
                val tempFile = File.createTempFile(
                    "temp_image_file_",
                    ".jpg",
                    intent.context.cacheDir
                )
                //
                val uri = FileProvider.getUriForFile(
                    intent.context,
                    "${BuildConfig.APPLICATION_ID}.provider", tempFile
                )
                _State.value = _State.value.copy(fileurl = uri)
            }

            is Intent.onImageSaved -> {
                val tempImageUrl = _State.value.fileurl
                if ( tempImageUrl !=null) {
                    val source = ImageDecoder.createSource(intent.context.contentResolver,tempImageUrl)

                    val currentPictures = _State.value.SelectedImages.toMutableList()
                    currentPictures.add(ImageDecoder.decodeBitmap(source).asImageBitmap())

                    _State.value = _State.value.copy(fileurl = null, SelectedImages = currentPictures)
                }
            }
        }

    }
}