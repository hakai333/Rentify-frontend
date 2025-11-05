package cl.MyMGroup.rentify.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.MyMGroup.rentify.data.dao.CartDao

class CartViewModelFactory(private val cartDao: CartDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(cartDao) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida ${modelClass.name}")
    }
}