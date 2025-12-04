package cl.MyMGroup.rentify.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.MyMGroup.rentify.data.dao.CartDao
import cl.MyMGroup.rentify.data.repository.PedidoRepository


class CartViewModelFactory(
    private val cartDao: CartDao,
    private val pedidoRepository: PedidoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)){
            @Suppress("UNCHECKED_LIST")
            return CartViewModel(cartDao, pedidoRepository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida ${modelClass.name}")
    }
}