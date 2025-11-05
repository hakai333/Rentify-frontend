package cl.MyMGroup.rentify.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import cl.MyMGroup.rentify.data.dao.UsuarioDao
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel(application: Application): AndroidViewModel(application){

    private val db = Room.databaseBuilder(
        application,
        RentifyDataBase::class.java,
        "rentify_db"
    ).build()

    private val usuarioDao: UsuarioDao = db.usuarioDao()


    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle);
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow();

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.value = LoginState.Loading;
            try {
                // Validaciones
                val usuario = usuarioDao.getUsuario(email, password);
                withContext(Dispatchers.Main) {
                    if(usuario != null) {
                        _loginState.value = LoginState.Success;
                        kotlinx.coroutines.delay(500);
                    } else {
                        _loginState.value = LoginState.Error("Credenciales invalidas!")
                    }
                }




            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _loginState.value = LoginState.Error("Error al acceder a la base de datos")
                }
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