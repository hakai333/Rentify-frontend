package cl.MyMGroup.rentify.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.entity.PackEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AmplificacionViewModel(application: Application) : AndroidViewModel(application) {

    private val db = RentifyDataBase.getInstance(application)

    private val _packs = MutableStateFlow<List<PackEntity>>(emptyList())
    val packs: StateFlow<List<PackEntity>> = _packs

    init {
        viewModelScope.launch {
            val existing = db.packDao().getPacksByCategoria("Amplificación y Pantallas")
            if (existing.isEmpty()) {
                val packsList = listOf(
                    PackEntity(
                        categoria = "Amplificación y Pantallas",
                        nombre = "Pack Sonido Básico",
                        descripcion = """
                            Para eventos pequeños. Incluye:
                            - 1 parlante y micrófono
                            - Cableado básico
                        """.trimIndent(),
                        precio = 49_990,
                        destacado = false
                    ),
                    PackEntity(
                        categoria = "Amplificación y Pantallas",
                        nombre = "Pack Sonido Estándar",
                        descripcion = """
                            Para eventos medianos. Incluye:
                            - 2 parlantes y 2 micrófonos
                            - Mezcladora básica
                            - Cables y soporte
                        """.trimIndent(),
                        precio = 99_990,
                        destacado = false
                    ),
                    PackEntity(
                        categoria = "Amplificación y Pantallas",
                        nombre = "Pack Sonido Premium",
                        descripcion = """
                            Para eventos grandes. Incluye:
                            - 4 parlantes y 4 micrófonos
                            - Mezcladora profesional
                            - Pantalla LED pequeña
                            - Soportes y cableado completo
                        """.trimIndent(),
                        precio = 199_990,
                        destacado = true
                    )
                )
                db.packDao().insertAll(packsList)
            }
            _packs.value = db.packDao().getPacksByCategoria("Amplificación y Pantallas")
        }
    }
}
