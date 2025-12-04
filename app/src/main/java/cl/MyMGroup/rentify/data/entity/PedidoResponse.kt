package cl.MyMGroup.rentify.data.entity

data class PedidoResponse(
    val pedidoId: Long,
    val usuarioId: Long,
    val detalle: String,
    val total: Double,
    val direccion: String,
    val fecha: String,
    val estado: String
)

