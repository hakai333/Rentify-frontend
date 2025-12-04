package cl.MyMGroup.rentify.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.dao.CartDao
import cl.MyMGroup.rentify.data.entity.CartItemEntity
import cl.MyMGroup.rentify.data.entity.PackEntity
import cl.MyMGroup.rentify.data.entity.PedidoRequest
import cl.MyMGroup.rentify.data.repository.PedidoRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartDao: CartDao,
    private val pedidoRepository: PedidoRepository // nuevo
) : ViewModel() {

    val cartItems: StateFlow<List<CartItemEntity>> = cartDao.getAllItems()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addToCart(pack: PackEntity) {
        viewModelScope.launch {
            val existingItem = cartDao.getItemPackId(pack.id)
            if (existingItem != null) {
                val updateItem = existingItem.copy(cantidad = existingItem.cantidad + 1)
                cartDao.updateItem(updateItem)
                // DEPURACION
                println("DEBUG - Item actualizado: $updateItem")
            } else {
                val newItem = CartItemEntity(
                    packId = pack.id,
                    nombre = pack.nombre,
                    precio = pack.precio,
                    cantidad = 1,
                    categoria = pack.categoria ?: "N/A"
                )
                cartDao.insertItem(newItem)
                // DEPURACION
                println("DEBUG - Item insertado: $newItem")
            }
        }
    }

    fun removeFromCart(item: CartItemEntity) {
        viewModelScope.launch {
            if (item.cantidad > 1) {
                val updateItem = item.copy(cantidad = item.cantidad - 1)
                cartDao.updateItem(updateItem)
            } else {
                cartDao.deleteItem(item)
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartDao.clearCart()
        }
    }

    fun getTotal(): Int {
        return cartItems.value.sumOf { it.precio * it.cantidad }
    }

    // ---------------- NUEVO: Realizar Pedido ----------------
    private val _pedidoMessage = MutableStateFlow<String?>(null)
    val pedidoMessage: StateFlow<String?> = _pedidoMessage

    fun realizarPedido(
        usuarioId: Long,
        direccion: String
    ) {
        viewModelScope.launch {
            try {
                if (cartItems.value.isEmpty()) {
                    _pedidoMessage.value = "El carrito está vacío"
                    return@launch
                }

                val detalleJson = Gson().toJson(
                    cartItems.value.map { item ->
                        mapOf(
                            "packId" to item.packId,
                            "cantidad" to item.cantidad
                        )
                    }
                )

                val pedidoRequest = PedidoRequest(
                    usuarioId = usuarioId,
                    detalle = detalleJson,
                    total = getTotal().toDouble(),
                    direccion = direccion
                )

                val response = pedidoRepository.enviarPedido(pedidoRequest)

                if (response.isSuccessful) {
                    _pedidoMessage.value = "Pedido realizado con éxito"
                    clearCart()
                } else {
                    _pedidoMessage.value = "Error al realizar pedido: ${response.code()}"
                }
            } catch (e: Exception) {
                _pedidoMessage.value = "Excepción: ${e.message}"
            }
        }
    }
    fun clearPedidoMessage() {
        _pedidoMessage.value = null
    }


}
