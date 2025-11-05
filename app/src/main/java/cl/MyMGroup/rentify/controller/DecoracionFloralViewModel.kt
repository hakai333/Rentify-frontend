package cl.MyMGroup.rentify.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.entity.PackEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.room.Room
import cl.MyMGroup.rentify.R
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
            if (existing.isEmpty()) {
                val floralPacks = listOf(

                    PackEntity(
                        categoria = "Decoracion Floral",
                        nombre = "Esencia Natural",
                        descripcion = """
                        Ideal para eventos pequeños o celebraciones íntimas. Incluye:
                        - Centros de mesa florales simples (flores de temporada)
                        - Arreglo principal para mesa de recepción
                        - Arreglo para entrada o altar
                        - Asesoría básica en combinación de colores
                    """.trimIndent(),

                        precio = 120_000,
                        destacado = true
                    ),
                    PackEntity(
                        categoria = "Decoracion Floral",
                        nombre = "Elegancia Floral",
                        descripcion = """
                        Perfecto para matrimonios o eventos formales. Incluye:
                        - Centros de mesa premium
                        - Arreglo principal + decoración floral en mesas secundarias
                        - Camino floral para pasillo o entrada
                        - Ramo de novia o arreglo especial para anfitrión/a
                        - Coordinación con la paleta del evento
                    """.trimIndent(),

                        precio = 250_000,
                        destacado = false
                    ),
                    PackEntity(
                        categoria = "Decoracion Floral",
                        nombre = "Luxury Garden",
                        descripcion = """
                        Ambientación completa con flores naturales de alta gama. Incluye:
                        - Decoración floral integral del lugar (entrada, escenario, mesas, bar, etc.)
                        - Arreglos suspendidos o estructuras florales
                        - Diseño personalizado con flores exóticas
                        - Servicio de montaje y desmontaje completo
                    """.trimIndent(),
                        precio = 400_000,
                        destacado = false
                    )
                )
                db.packDao().insertAll(floralPacks)
            }
            _packs.value = db.packDao().getPacksByCategoria("Decoracion Floral")
        }
    }
}