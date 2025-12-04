package cl.MyMGroup.rentify.data.repository

import cl.MyMGroup.rentify.data.api.ApiService
import cl.MyMGroup.rentify.data.entity.PedidoRequest
import cl.MyMGroup.rentify.data.entity.PedidoResponse
import retrofit2.Response

class PedidoRepository(private val apiService: ApiService) {

    // Enviar pedido al backend
    suspend fun enviarPedido(pedido: PedidoRequest): Response<PedidoResponse> {
        return apiService.enviarPedido(pedido)
    }

    suspend fun obtenerPedidos(usuarioId: Long): Response<List<PedidoResponse>> {
        return apiService.getPedidosPorUsuario(usuarioId)
    }
}

