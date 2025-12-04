package cl.MyMGroup.rentify.data.entity

class PedidoRequest(
    val usuarioId: Long,
    val detalle: String, // AQUI SE ALMACENARAN COMO JSON -- IMPORTANTE --
    val total: Double,
    val direccion: String
)

