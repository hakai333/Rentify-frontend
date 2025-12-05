package cl.MyMGroup.rentify.controller

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PacksDestacadosViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PacksDestacadosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PacksDestacadosViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
