package cl.MyMGroup.rentify.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.entity.PackEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PacksDestacadosViewModel(application: Application) : AndroidViewModel(application) {

    private val db = RentifyDataBase.getInstance(application)
    private val _packsDestacados = MutableStateFlow<List<PackEntity>>(emptyList())
    val packsDestacados: StateFlow<List<PackEntity>> = _packsDestacados

    init {
        viewModelScope.launch {
            _packsDestacados.value = db.packDao().getDestacados()
        }
    }
}
