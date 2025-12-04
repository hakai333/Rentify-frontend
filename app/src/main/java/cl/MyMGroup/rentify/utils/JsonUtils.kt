package cl.MyMGroup.rentify.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class DetallePedidoItem(
    val packId: Long,
    val cantidad: Int
)

fun parseDetalleJson(detalle: String): List<DetallePedidoItem> {
    return try {
        val type = object : TypeToken<List<DetallePedidoItem>>() {}.type
        Gson().fromJson(detalle, type)
    } catch (e: Exception) {
        emptyList()
    }
}
