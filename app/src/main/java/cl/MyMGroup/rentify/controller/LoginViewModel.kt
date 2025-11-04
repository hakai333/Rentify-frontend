package cl.MyMGroup.rentify.controller

import android.os.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle);
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow();

    fun login(
        email: String,
        password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading;
            kotlinx.coroutines.delay(1500);

            if( email.isNotEmpty() && password.length >= 6 ) {
                _loginState.value = LoginState.Success;
            } else {
                _loginState.value = LoginState.Error("Credenciales invalidas!")
            }

        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle;
    }
}

sealed class LoginState {
    object Idle: LoginState();
    object Loading: LoginState();
    object  Success: LoginState();
    data class Error(val message: String) : LoginState();
}