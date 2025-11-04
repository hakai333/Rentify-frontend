package cl.MyMGroup.rentify.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.entity.PackEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.StateFlow

class DecoracionFloralViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        RentifyDataBase::class.java,
        "rentify_db"
    ).build()

    private val _packs = MutableStateFlow<List<PackEntity>>(emptyList())
    val packs: StateFlow<List<PackEntity>> = _packs

    init {
        viewModelScope.launch {
            val existing = db.packDao().getPacksByCategoria("Decoracion Floral")
            if(existing.isEmpty()){
                val floralPacks = listOf(
                    PackEntity(
                        categoria = "Decoracion Floral",
                        nombre = "Esencia Natural",
                        descripcion = "Una decoracion ideal para eventos pequeños o celebraciones ìntimas.",
                        precio = 120000.0,
                        destacado = true
                    ),
                    PackEntity(
                        categoria = "Decoracion Floral",
                        nombre = "Elegancia Floral",
                        descripcion = "Perfecto para matrimonios o eventos formales",
                        precio = 250000.0,
                        destacado = false
                    ),
                    PackEntity(
                        categoria = "Decoracion Floral",
                        nombre = "Luxury Garden",
                        descripcion = "Ambientancion completamente con flores naturales de alta gama.",
                        precio = 400000.0,
                        destacado = false
                    )
                )
                db.packDao().insertAll(floralPacks)
            }

            _packs.value = db.packDao().getPacksByCategoria("Decoracion Floral")
        }
    }
}