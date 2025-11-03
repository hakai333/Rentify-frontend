package cl.MyMGroup.rentify.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistroViewModel : ViewModel() {

    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState.asStateFlow()

    fun registrarUsuario(
        nombre: String,
        email: String,
        password: String,
        confirmarPassword: String
    ) {
        viewModelScope.launch {
            _registroState.value = RegistroState.Loading

            // Validaciones
            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
                _registroState.value = RegistroState.Error("Todos los campos son obligatorios")
                return@launch
            }

            if (password != confirmarPassword) {
                _registroState.value = RegistroState.Error("Las contraseñas no coinciden")
                return@launch
            }

            if (password.length < 6) {
                _registroState.value = RegistroState.Error("La contraseña debe tener al menos 6 caracteres")
                return@launch
            }

            // Simulación de registro exitoso
            kotlinx.coroutines.delay(1500)
            _registroState.value = RegistroState.Success
        }
    }

    fun resetState() {
        _registroState.value = RegistroState.Idle
    }
}

sealed class RegistroState {
    object Idle : RegistroState()
    object Loading : RegistroState()
    object Success : RegistroState()
    data class Error(val message: String) : RegistroState()
}