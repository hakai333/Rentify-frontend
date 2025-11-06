package cl.MyMGroup.rentify.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import cl.MyMGroup.rentify.data.dao.UsuarioDao
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.entity.LoginRequest
import cl.MyMGroup.rentify.data.entity.UsuarioEntity
import cl.MyMGroup.rentify.data.network.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    private val api = RetrofitProvider.authService

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        // validacion campos vacius
        if (email.isEmpty() && password.isEmpty()) {
            _loginState.value = LoginState.Error("Correo y contraseña están vacíos")
            return
        } else if (email.isEmpty()) {
            _loginState.value = LoginState.Error("Correo vacío")
            return
        } else if (password.isEmpty()) {
            _loginState.value = LoginState.Error("Contraseña vacía")
            return
        }

        // ejecuta la llamada a la API
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading
                val response = api.login(LoginRequest(email, password))

                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        _loginState.value = LoginState.Success(usuario)
                    } else {
                        _loginState.value = LoginState.Error("Respuesta vacía del servidor")
                    }
                } else {
                    if (response.code() == 401) {
                        _loginState.value = LoginState.Error("Correo o contraseña inválida")
                    } else {
                        _loginState.value = LoginState.Error("Error ${response.code()}: ${response.message()}")
                    }
                }

            } catch (ex: Exception) {
                _loginState.value = LoginState.Error("Error de conexión: ${ex.message}")
            }
        }
    }


    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val usuario: UsuarioEntity) : LoginState()
    data class Error(val message: String) : LoginState()
}