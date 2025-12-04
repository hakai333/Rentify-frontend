import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.MyMGroup.rentify.controller.CartViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import cl.MyMGroup.rentify.controller.LoginState
import cl.MyMGroup.rentify.controller.LoginViewModel
import cl.MyMGroup.rentify.controller.PedidoViewModel
import cl.MyMGroup.rentify.utils.parseDetalleJson
import cl.MyMGroup.rentify.view.formatPrecioCLP



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CotizacionScreen(
    navController: NavController,
    pedidoViewModel: PedidoViewModel,
    loginViewModel: LoginViewModel,
    onBack: () -> Unit
) {
    val pedidos by pedidoViewModel.pedidos.collectAsState()
    val loginState by loginViewModel.loginState.collectAsState()


    LaunchedEffect(Unit) {
        val usuarioId = when (loginState) {
            is LoginState.Success -> (loginState as LoginState.Success).usuario.id
            else -> null
        }
        if (usuarioId != null) {
            pedidoViewModel.cargarPedidos(usuarioId) // ahora es seguro, Long no nullable
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Pedidos") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                if (pedidos.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No tienes pedidos confirmados",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(pedidos) { pedido ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text("Pedido #${pedido.pedidoId}", style = MaterialTheme.typography.titleMedium)
                                    Text("Dirección: ${pedido.direccion}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Total: ${formatPrecioCLP(pedido.total.toInt())}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Detalle:", style = MaterialTheme.typography.bodyMedium)

                                    val detalleParseado = parseDetalleJson(pedido.detalle)

                                    detalleParseado.forEach { pack ->
                                        Text(
                                            "- PackId: ${pack.packId}, Cantidad: ${pack.cantidad}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
