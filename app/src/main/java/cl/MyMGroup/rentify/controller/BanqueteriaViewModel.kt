package cl.MyMGroup.rentify.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.entity.PackEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BanqueteriaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = RentifyDataBase.getInstance(application)

    private val _packs = MutableStateFlow<List<PackEntity>>(emptyList())
    val packs: StateFlow<List<PackEntity>> = _packs

    init {
        viewModelScope.launch {
            val existing = db.packDao().getPacksByCategoria("Banquetería")
            if (existing.isEmpty()) {
                val packsList = listOf(
                    PackEntity(
                        categoria = "Banquetería",
                        nombre = "Pack Banquete Básico",
                        descripcion = """
                            Para eventos pequeños. Incluye:
                            - Servicio de comida para 10 personas
                            - Vajilla y cubiertos básicos
                            - Atención básica
                        """.trimIndent(),
                        precio = 69_990,
                        destacado = true
                    ),
                    PackEntity(
                        categoria = "Banquetería",
                        nombre = "Pack Banquete Estándar",
                        descripcion = """
                            Para eventos medianos. Incluye:
                            - Servicio de comida para 30 personas
                            - Vajilla y cubiertos estándar
                            - Personal de apoyo
                        """.trimIndent(),
                        precio = 149_990,
                        destacado = false
                    ),
                    PackEntity(
                        categoria = "Banquetería",
                        nombre = "Pack Banquete Premium",
                        descripcion = """
                            Para eventos grandes. Incluye:
                            - Servicio de comida para 60 personas
                            - Vajilla y cubiertos premium
                            - Personal completo y decoración básica
                        """.trimIndent(),
                        precio = 299_990,
                        destacado = false
                    )
                )
                db.packDao().insertAll(packsList)
            }
            _packs.value = db.packDao().getPacksByCategoria("Banquetería")
        }
    }
}
