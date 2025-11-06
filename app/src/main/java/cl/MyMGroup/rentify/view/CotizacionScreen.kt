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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CotizacionScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    onBack: () -> Unit
) {
    val items by cartViewModel.cartItems.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope() // Para lanzar coroutines

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Cotización") },
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
                            "No hay elementos en el carrito",
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
                                        "Categoría: ${item.categoria ?: "N/A"}",
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

                    Text(
                        "Total: ${formatPrecioCLP(cartViewModel.getTotal())}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón confirmar cotización
                    Button(
                        onClick = {
                            scope.launch {
                                // Mostrar Snackbar
                                snackbarHostState.showSnackbar("¡Cotización realizada con éxito!")
                                // Limpiar carrito después de mostrar el mensaje
                                cartViewModel.clearCart()


                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text("Confirmar Cotización")
                    }
                }
            }
        }
    )
}

// Formateo de precio a CLP
fun formatPrecioCLP(valor: Int): String {
    return "$" + "%,d".format(valor).replace(',', '.')
}
