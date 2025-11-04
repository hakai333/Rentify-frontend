package cl.MyMGroup.rentify.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packs")
data class PackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoria: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val destacado: Boolean = false
)
