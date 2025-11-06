package cl.MyMGroup.rentify.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.entity.PackEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.room.Room
import kotlinx.coroutines.flow.StateFlow

class MobiliarioViewModel(application: Application) : AndroidViewModel(application) {

    private val db = RentifyDataBase.getInstance(application)

    private val _packs = MutableStateFlow<List<PackEntity>>(emptyList())
    val packs: StateFlow<List<PackEntity>> = _packs

    init {
        viewModelScope.launch {
            val existing = db.packDao().getPacksByCategoria("Arriendo Mobiliario")
            if (existing.isEmpty()) {
                val floralPacks = listOf(

                    PackEntity(
                        categoria = "Arriendo Mobililario",
                        nombre = "Pack Fiesta Básica",
                        descripcion = """
                        Ideal para reuniones pequeñas o celebraciones familiares. Incluye lo esencial para una fiesta cómoda y funcional para hasta 10 personas.. Incluye:
                        - 1 mesa rectangular y 10 sillas plegables
                        - 10 platos, vasos y juegos de cubiertos
                        - Mantel básico blancor
                        - 1 mesa auxiliar para snacks o bebidas
                        - 1 set de cristalería simple (copas de vino y vasos)
                        - 1 basurero pequeño
                        - 1 arreglo de centro de mesa sencillo
                    """.trimIndent(),

                        precio = 39_990,
                        destacado = false
                    ),
                    PackEntity(
                        categoria = "Arriendo Mobililario",
                        nombre = "Pack Fiesta Estándar",
                        descripcion = """
                       Perfecto para celebraciones medianas o cumpleaños con amigos. Equipado para 20 personas, con mobiliario más cómodo y detalles decorativos. Incluye:
                        - 2 mesas rectangulares y 20 sillas de resina
                        - 20 juegos de platos, vasos y cubiertos de acero inoxidable
                        - Manteles de color a elección
                        - 1 set de lounge de madera (2 bancas + 1 mesa baja)
                        - Cristalería estándar (copas de vino, champagne y vasos de trago largo)
                        - 1 mesa auxiliar para buffet o bar
                        - 3 arreglos de mesa decorativos
                    """.trimIndent(),

                        precio = 69_990,
                        destacado = true
                    ),
                    PackEntity(
                        categoria = "Arriendo Mobililario",
                        nombre = "Pack Fiesta Premium",
                        descripcion = """
                        Pensado para eventos más amplios y elegantes. Equipamiento para 40 personas, con mobiliario de mejor calidad y detalles de ambientación. Incluye:
                        - 4 mesas rectangulares o redondas + 40 sillas acolchadas
                        - 40 juegos de vajilla completa (plato principal, postre, cubiertos, copas)
                        - Mantelería premium y caminos de mesa
                        - Lounge completo de madera con cojines
                        - Decoración intermedia con luces cálidas
                        - Estación de bar o cocina móvil básica
                        - 6 arreglos de mesa decorativos
                    """.trimIndent(),
                        precio = 129_990,
                        destacado = true
                    ),
                    PackEntity(
                        categoria = "Arriendo Mobililario",
                        nombre = "Pack Fiesta Deluxe",
                        descripcion = """
                        Diseñado para matrimonios, cenas formales o eventos empresariales de hasta 80 personas. Combina comodidad, elegancia y funcionalidad. Incluye:
                        - 8 mesas redondas y 80 sillas tapizadas
                        - Vajilla completa con copas de cristal y cubiertos de acero pulido
                        - Mantelería satinada o lino
                        - Lounge extendido con sillones y mesas laterales
                        - Decoración elegante (luces, centros de mesa, flores artificiales o naturales)
                        - Estación de cocina/bar profesional
                        - Cristalería premium
                    """.trimIndent(),
                        precio = 249_990,
                        destacado = true
                    ),
                    PackEntity(
                        categoria = "Arriendo Mobililario",
                        nombre = "Pack Fiesta Élite",
                        descripcion = """
                        El paquete más completo, ideal para eventos masivos, matrimonios grandes o celebraciones corporativas de hasta 150 personas. Ofrece mobiliario y decoración de nivel profesional. Incluye:
                        - 15 mesas redondas y 150 sillas tapizadas premium
                        - Vajilla y cristalería de lujo completa
                        - Mantelería de lino fino y caminos personalizados
                        - Lounge VIP con sillones, bancas y mesas decorativas
                        - Decoración integral con luces, flores, guirnaldas y ambientación temática
                        - Estación de cocina/bar profesional + utensilios
                        - Mesas de apoyo para buffet y postres
                    """.trimIndent(),
                        precio = 499_990,
                        destacado = true
                    )
                )
                db.packDao().insertAll(floralPacks)
            }
            _packs.value = db.packDao().getPacksByCategoria("Arriendo Mobililario")
        }
    }
}