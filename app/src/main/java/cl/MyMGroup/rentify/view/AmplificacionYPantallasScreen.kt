package cl.MyMGroup.rentify.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.MyMGroup.rentify.controller.AmplificacionViewModel
import cl.MyMGroup.rentify.controller.CartViewModel
import cl.MyMGroup.rentify.data.entity.PackEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmplificacionYPantallasScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val viewModel: AmplificacionViewModel = viewModel()
    val packs by viewModel.packs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Amplificación y pantallas") },
                actions = {
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        },
        content = { padding ->
            if (packs.isEmpty()) {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    Modifier.fillMaxSize().padding(padding).padding(16.dp)
                ) {
                    items(packs) { pack ->
                        PackCard(pack, cartViewModel)
                    }
                }
            }
        }
    )
}
@Composable
fun PackCard(pack: PackEntity, cartViewModel: CartViewModel) {
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(pack.nombre, style = MaterialTheme.typography.titleMedium)
            Text(pack.descripcion, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text("Precio: $${pack.precio}", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))
            Button(onClick = { cartViewModel.addToCart(pack) }) {
                Text("Agregar al carrito")
            }
        }
    }
}
