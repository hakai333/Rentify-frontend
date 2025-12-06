package cl.MyMGroup.rentify.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.network.RetrofitProvider
import cl.MyMGroup.rentify.data.entity.RegisterRequest
import cl.MyMGroup.rentify.view.RegistroScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RegistroState {
    object Idle : RegistroState()
    object Loading : RegistroState()
    data class Success(val message: String) : RegistroState()
    data class Error(val message: String) : RegistroState()
}


class RegistroViewModel : ViewModel() {

    private val api = RetrofitProvider.apiService

    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState.asStateFlow()
    val dominiosPermitidos = listOf("@gmail.com", "@outlook.com", "@yahoo.com")



    fun register(
            nombre: String,
            apellido: String,
            email: String,
            password: String,
            confirmarPassword: String) {


        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            _registroState.value = RegistroState.Error("Por favor completa todos los campos")
            return
        }
        if (password.length <= 6) {
            _registroState.value = RegistroState.Error("La contraseña es muy corta")
            return
        }

        if (!email.contains("@")) {
            _registroState.value = RegistroState.Error("Email invalido")
            return
        }

        if (password != confirmarPassword) {
            _registroState.value = RegistroState.Error("Las contraseñas no coinciden")
            return
        }
        if (dominiosPermitidos.none { email.endsWith(it) }) {
            _registroState.value = RegistroState.Error("Email no permitido. Usa Gmail, Outlook o Yahoo")
            return
        }



        viewModelScope.launch {
            try {
                _registroState.value = RegistroState.Loading
                val response = api.register(RegisterRequest(nombre, email, password))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        when (body.mensaje) {
                            "Registrado correctamente" -> _registroState.value = RegistroState.Success(body.mensaje)
                            else -> _registroState.value = RegistroState.Error(body.mensaje)
                        }
                    } else {
                        _registroState.value = RegistroState.Error("Respuesta vacía del servidor")
                    }
                } else {
                    _registroState.value = RegistroState.Error("Error en servidor: ${response.code()}")
                }
            } catch (ex: Exception) {
                _registroState.value = RegistroState.Error("Error al registrarse: ${ex.message}")
            }
        }

    }

    fun resetState() {
        _registroState.value = RegistroState.Idle
    }
}
