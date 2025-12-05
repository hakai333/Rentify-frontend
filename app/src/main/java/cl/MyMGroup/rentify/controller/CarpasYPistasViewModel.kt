package cl.MyMGroup.rentify.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.entity.PackEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarpasYPistasViewModel(application: Application) : AndroidViewModel(application) {

    private val db = RentifyDataBase.getInstance(application)

    private val _packs = MutableStateFlow<List<PackEntity>>(emptyList())
    val packs: StateFlow<List<PackEntity>> = _packs

    init {
        viewModelScope.launch {
            val existing = db.packDao().getPacksByCategoria("Carpas y Pistas")
            if (existing.isEmpty()) {
                val packsList = listOf(
                    PackEntity(
                        categoria = "Carpas y Pistas",
                        nombre = "Pack Carpa Básica",
                        descripcion = """
                            Ideal para reuniones pequeñas al aire libre. Incluye:
                            - 1 carpa de 4x4 metros
                            - Piso base para protección
                            - Estructura ligera y fácil de montar
                        """.trimIndent(),
                        precio = 79_990,
                        destacado = false
                    ),
                    PackEntity(
                        categoria = "Carpas y Pistas",
                        nombre = "Pack Carpa Estándar",
                        descripcion = """
                            Para eventos medianos y cumpleaños. Incluye:
                            - 2 carpas de 6x6 metros
                            - Pista de baile básica 4x4 metros
                            - Iluminación LED simple
                        """.trimIndent(),
                        precio = 149_990,
                        destacado = true
                    ),
                    PackEntity(
                        categoria = "Carpas y Pistas",
                        nombre = "Pack Carpa Premium",
                        descripcion = """
                            Para bodas o eventos grandes. Incluye:
                            - 3 carpas de 8x8 metros
                            - Pista de baile 6x6 metros
                            - Iluminación LED + decoración básica
                        """.trimIndent(),
                        precio = 249_990,
                        destacado = false
                    )
                )
                db.packDao().insertAll(packsList)
            }
            _packs.value = db.packDao().getPacksByCategoria("Carpas y Pistas")
        }
    }
}
