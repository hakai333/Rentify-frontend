package cl.MyMGroup.rentify.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.MyMGroup.rentify.data.entity.PedidoResponse
import cl.MyMGroup.rentify.data.repository.PedidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PedidoViewModel(private val pedidoRepository: PedidoRepository) : ViewModel() {

    private val _pedidos = MutableStateFlow<List<PedidoResponse>>(emptyList())
    val pedidos: StateFlow<List<PedidoResponse>> = _pedidos

    fun cargarPedidos(usuarioId: Long) {
        viewModelScope.launch {
            try {
                val response = pedidoRepository.obtenerPedidos(usuarioId)
                if (response.isSuccessful) {
                    _pedidos.value = response.body() ?: emptyList()
                } else {
                    _pedidos.value = emptyList()
                }
            } catch (e: Exception) {
                _pedidos.value = emptyList()
            }
        }
    }


}
