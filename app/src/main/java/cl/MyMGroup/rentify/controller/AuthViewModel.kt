package cl.MyMGroup.rentify.controller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.entity.RegisterRequest
import cl.MyMGroup.rentify.data.network.RetrofitProvider
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val api = RetrofitProvider.apiService

    fun register(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = api.register(RegisterRequest(nombre, email, password))
                if (response.isSuccessful) {
                    Log.d("TEST", "Response: ${response.body()}")
                } else {
                    Log.e("TEST", "Error en el servidor: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("TEST", "Error: ${e.message}")
            }
        }
    }
}
