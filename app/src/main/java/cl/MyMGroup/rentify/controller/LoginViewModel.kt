package cl.MyMGroup.rentify.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.api.ApiService
import cl.MyMGroup.rentify.data.entity.LoginRequest
import cl.MyMGroup.rentify.data.entity.Usuario
import cl.MyMGroup.rentify.data.entity.UsuarioEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Estados de login
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val usuario: Usuario) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val authResponse = response.body()!!
                    val usuario = authResponse.usuario
                    if (usuario != null) {
                        _loginState.value = LoginState.Success(usuario)
                    } else {
                        _loginState.value = LoginState.Error(authResponse.mensaje)
                    }
                } else {
                    _loginState.value = LoginState.Error("Email o contraseña incorrectos")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}
