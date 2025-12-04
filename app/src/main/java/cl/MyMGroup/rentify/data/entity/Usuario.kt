package cl.MyMGroup.rentify.data.entity

data class Usuario (
    val id: Long,
    val nombre: String,
    val apellido: String,
    val email: String,
    val password: String
)