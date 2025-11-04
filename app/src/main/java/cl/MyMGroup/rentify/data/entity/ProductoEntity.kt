package cl.MyMGroup.rentify.data.entity

import androidx.room.Entity

@Entity(tableName = "productos")
data class ProductoEntity (
    val id: Int = 0,
    val nombre: String,
    val categoria: String
)

