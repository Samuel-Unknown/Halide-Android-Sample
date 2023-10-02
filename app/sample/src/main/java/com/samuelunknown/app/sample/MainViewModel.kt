package com.samuelunknown.app.sample

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.samuelunknown.app.features.processing.GetBitmapUseCase
import com.samuelunknown.app.features.processing.GetBitmapUseCaseImpl
import com.samuelunknown.app.features.processing.InvertBitmapUseCase
import com.samuelunknown.app.features.processing.InvertBitmapUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class MainState(
    val bitmap: Bitmap,
    val isInverted: Boolean
)

class MainViewModel(applicationContext: Context) : ViewModel() {
    private val getBitmapUseCase: GetBitmapUseCase = GetBitmapUseCaseImpl(applicationContext)
    private val invertBitmapUseCase: InvertBitmapUseCase = InvertBitmapUseCaseImpl()

    val stateFlow: MutableStateFlow<MainState> = MutableStateFlow(
        MainState(
            bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
            isInverted = false
        )
    )

    init {
        viewModelScope.launch {
            val originalBitmap = getBitmapUseCase.execute(IMAGE_ASSET)
            stateFlow.value = stateFlow.value.copy(bitmap = originalBitmap)
        }
    }

    fun invertImage() {
        viewModelScope.launch {
            val isInverted = stateFlow.value.isInverted.not()
            val originalBitmap = getBitmapUseCase.execute(IMAGE_ASSET)
            val outputBitmap = if (isInverted) {
                invertBitmapUseCase.execute(originalBitmap)
            } else {
                originalBitmap
            }

            stateFlow.value = stateFlow.value.copy(
                bitmap = outputBitmap,
                isInverted = isInverted
            )
        }
    }

    companion object {
        private const val IMAGE_ASSET = "file:///android_asset/image.png"

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                return MainViewModel(application) as T
            }
        }
    }
}