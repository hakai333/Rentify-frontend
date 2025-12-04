package cl.MyMGroup.rentify.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.MyMGroup.rentify.controller.CartViewModel
import cl.MyMGroup.rentify.controller.LoginState
import cl.MyMGroup.rentify.controller.LoginViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    cartViewModel: CartViewModel,
    loginViewModel: LoginViewModel,
    navController: NavController,
    onBack: () -> Unit
) {
    println("DEBUG - Iniciando CarritoScreen")

    val items by cartViewModel.cartItems.collectAsState()
    val loginState by loginViewModel.loginState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Usuario logueado
    val usuarioId = (loginState as? LoginState.Success)?.usuario?.id ?: run {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Debes iniciar sesión")
        }
        return
    }

    // Dirección del usuario
    var direccion by remember { mutableStateOf("") }

    // Escucha mensajes de pedido
    val pedidoMessage by cartViewModel.pedidoMessage.collectAsState()
    LaunchedEffect(pedidoMessage) {
        pedidoMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            cartViewModel.clearPedidoMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Rentify") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                if (items.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Tu carrito está vacío",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(items) { item ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(item.nombre, style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        "Categoría: ${item.categoria}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text("Cantidad: ${item.cantidad}", style = MaterialTheme.typography.bodySmall)
                                    Text("Precio unitario: ${formatPrecioCLP(item.precio)}", style = MaterialTheme.typography.bodySmall)
                                    Text(
                                        "Subtotal: ${formatPrecioCLP(item.precio * item.cantidad)}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección de entrega") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Total: ${formatPrecioCLP(cartViewModel.getTotal())}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (direccion.isBlank()) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Debes ingresar una dirección")
                                }
                            } else {
                                println("DEBUG USUARIO ID -> $usuarioId")
                                println("DEBUG LOGIN STATE -> $loginState")
                                if (usuarioId == null || usuarioId == 0L) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Error: usuario no válido")
                                    }
                                } else {
                                    cartViewModel.realizarPedido(
                                        usuarioId = usuarioId,
                                        direccion = direccion
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text("Confirmar Compra")
                    }
                }
            }
        }
    )
}

// Función de ayuda para formatear precio a CLP
fun formatPrecioCLP(valor: Int): String {
    return "$" + "%,d".format(valor).replace(',', '.')
}
