package cl.MyMGroup.rentify.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.MyMGroup.rentify.data.entity.PackEntity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecoracionFloralScreen(navController: NavController) {
    val viewModel: DecoracionFloralViewModel = viewModel()
    val packs by viewModel.packs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Decoración Floral") })
        },
        content = { padding ->
            if (packs.isEmpty()) {
                // Muestra un loading o mensaje si la BD aún no está lista
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    items(packs) { pack ->
                        PackCard(pack = pack, onAddToCart = {
                            // TODO: aquí se conectará con el carrito
                        })
                    }
                }
            }
        }
    )
}

@Composable
fun PackCard(pack: PackEntity, onAddToCart: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(pack.nombre, style = MaterialTheme.typography.titleMedium)
            Text(pack.descripcion, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text("Precio: $${pack.precio}", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))
            Button(onClick = onAddToCart) {
                Text("Agregar al carrito")
            }
        }
    }
}
