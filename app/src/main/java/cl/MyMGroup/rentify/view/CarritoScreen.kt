@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package cl.MyMGroup.rentify.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.MyMGroup.rentify.controller.CartViewModel
import cl.MyMGroup.rentify.data.entity.CartItemEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onRealizarCotizacion: () -> Unit
) {
    val items by cartViewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Rentify") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.Delete, contentDescription = "Volver")
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
                if (items.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Tu carrito está vacío", style = MaterialTheme.typography.bodyLarge)
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
                                    Text("Categoría: ${item.nombre}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Cantidad: ${item.cantidad}", style = MaterialTheme.typography.bodySmall)
                                    Text("Precio unitario: ${formatPrecioCLP(item.precio)}", style = MaterialTheme.typography.bodySmall)
                                    Text("Subtotal: ${formatPrecioCLP(item.precio * item.cantidad)}", style = MaterialTheme.typography.bodySmall)
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

                    Button(
                        onClick = onRealizarCotizacion,
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                    ) {
                        Text("Realizar Cotización")
                    }
                }
            }
        }
    )
}

fun formatPrecioCLP(valor: Int): String {
    return "$" + "%,d".format(valor).replace(',', '.')
}
