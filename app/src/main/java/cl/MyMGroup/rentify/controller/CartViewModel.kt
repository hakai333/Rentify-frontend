package cl.MyMGroup.rentify.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.dao.CartDao
import cl.MyMGroup.rentify.data.entity.CartItemEntity
import cl.MyMGroup.rentify.data.entity.PackEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private  val cartDao: CartDao) : ViewModel(){
    val cartItems: StateFlow<List<CartItemEntity>> = cartDao.getAllItems()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addToCart(pack: PackEntity) {
        viewModelScope.launch {
            val existingItem = cartDao.getItemPackId(pack.id)
            if(existingItem != null){
                val updateItem = existingItem.copy(cantidad = existingItem.cantidad + 1)
                cartDao.updateItem(updateItem)
            }else{
                val newItem = CartItemEntity(
                    packId = pack.id,
                    nombre = pack.nombre,
                    precio = pack.precio,
                    cantidad = 1,
                    //foto = pack.foto
                )
                cartDao.insertItem(newItem)
            }
        }
    }

    fun removeFromCart(item: CartItemEntity){
        viewModelScope.launch {
            if(item.cantidad > 1){
                val updateItem = item.copy(cantidad = item.cantidad - 1)
                cartDao.updateItem(updateItem)
            } else {
               cartDao.deleteItem(item)
            }
        }
    }

    fun clearCart(){
        viewModelScope.launch {
            cartDao.clearCart()
        }
    }

    fun getTotal(): Int{
        return cartItems.value.sumOf { it.precio * it.cantidad }
    }
}