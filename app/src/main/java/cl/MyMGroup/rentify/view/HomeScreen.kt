package cl.MyMGroup.rentify.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.MyMGroup.rentify.R

data class HomeOption(
    val title: String,
    val destination: String,
    val imageRes: Int
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    var mostrarScanner by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Rentify") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    actions = {
                        IconButton(onClick = { mostrarScanner = true }) {
                            Icon(
                                imageVector = Icons.Default.QrCodeScanner,
                                contentDescription = "Escanear QR"
                            )
                        }
                        IconButton(onClick = { navController.navigate("carrito") }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Carrito"
                            )
                        }
                    }
                )
            },
            content = { innerPadding ->
                LazyColumn(
                    contentPadding = innerPadding,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    val options = listOf(
                        HomeOption("Decoración floral", "decoracionFloral", R.drawable.floral),
                        HomeOption("Carpas y pistas de baile", "carpas", R.drawable.carpas),
                        HomeOption("Banquetería y produccion", "banqueteria", R.drawable.banqueteria),
                        HomeOption("Arriendo mobiliario", "mobiliario", R.drawable.arriendo),
                        HomeOption("Amplificacion y pantallas", "amplificacion", R.drawable.amplificacion)
                    )
                    items(options) { option ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate(option.destination) },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = option.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Image(
                                    painter = painterResource(id = option.imageRes),
                                    contentDescription = option.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        )

        // 🔹 QR Scanner sobre la pantalla
        if (mostrarScanner) {
            QrScannerScreen(
                onQrScanned = { qr ->
                    mostrarScanner = false
                    if (qr == "PACKS_DESTACADOS") {
                        navController.navigate("packsDestacados") {
                            launchSingleTop = true
                        }
                    } else {
                        println("QR no reconocido: $qr")
                    }
                },
                onClose = {
                    mostrarScanner = false
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
