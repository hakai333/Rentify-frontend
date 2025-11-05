package cl.MyMGroup.rentify.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.dao.UsuarioDao
import cl.MyMGroup.rentify.data.entity.UsuarioEntity
import cl.MyMGroup.rentify.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistroViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState.asStateFlow()

    fun registrarUsuario(
        nombre: String,
        apellido: String,
        email: String,
        password: String,
        confirmarPassword: String
    ) {
        viewModelScope.launch {
            _registroState.value = RegistroState.Loading

            // Validaciones
            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
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

            if (!email.contains("@")) {
                _registroState.value = RegistroState.Error("Correo invalido")
                return@launch
            }

            val nuevoUsuario = UsuarioEntity(
                nombre = nombre,
                apellido = apellido,
                email = email,
                password = password
            )

            repository.insertarUsuario(nuevoUsuario)


            // Simulación de registro exitoso
            kotlinx.coroutines.delay(500)
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