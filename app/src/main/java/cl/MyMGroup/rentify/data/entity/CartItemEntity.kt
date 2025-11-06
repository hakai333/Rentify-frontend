package cl.MyMGroup.rentify.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val packId: Int,
    val nombre: String,
    val precio: Int,
    val cantidad: Int = 1,
    val categoria: String
)
