package cl.MyMGroup.rentify.data.entity

data class AuthResponse(
    val mensaje: String,
    val token: String?,
    val usuario: Usuario?
)