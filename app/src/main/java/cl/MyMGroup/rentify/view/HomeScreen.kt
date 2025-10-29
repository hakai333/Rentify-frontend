package cl.MyMGroup.rentify.view

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController

// Pantalla principal con Scaffold
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val currentDestination = remember { mutableStateOf<BottomBarDestination>(BottomBarDestination.Home) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Rentify")
                }
            )
        },
        content = { innerPadding ->
            // Contenido de la pantalla
            HomeContent(innerPadding)
        },
        bottomBar = {
            HomeBottomBar(
                currentDestination = currentDestination.value,
                onDestinationSelected = { destination ->
                    currentDestination.value = destination
                    when (destination) {
                        BottomBarDestination.Home -> navController.navigate("home")
                        BottomBarDestination.Cart -> navController.navigate("cart")
                        BottomBarDestination.Profile -> navController.navigate("profile")
                    }
                }
            )
        }
    )
}

// Clase para los destinos del BottomBar
sealed class BottomBarDestination {
    object Home : BottomBarDestination()
    object Cart : BottomBarDestination()
    object Profile : BottomBarDestination()
}

// BottomBar
@Composable
fun HomeBottomBar(
    currentDestination: BottomBarDestination,
    onDestinationSelected: (BottomBarDestination) -> Unit
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround

        ) {
            BottomBarItem(
                icon = Icons.Default.ShoppingCart,
                label = "Carrito",
                isSelected = currentDestination == BottomBarDestination.Cart,
                onClick = { onDestinationSelected(BottomBarDestination.Cart) }
            )

            BottomBarItem(
                icon = Icons.Default.Home,
                label = "Inicio",
                isSelected = currentDestination == BottomBarDestination.Home,
                onClick = { onDestinationSelected(BottomBarDestination.Home) }
            )

            BottomBarItem(
                icon = Icons.Default.Person,
                label = "Perfil",
                isSelected = currentDestination == BottomBarDestination.Profile,
                onClick = { onDestinationSelected(BottomBarDestination.Profile) }
            )
        }
    }
}

// Item individual del BottomBar
@Composable
fun BottomBarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)

    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        )
        Text(text = label, color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
    }
}

// Contenido placeholder para que compile
@Composable
fun HomeContent(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Text("Contenido de la pantalla principal")
    }
}

// Vista previa funcional
@Preview(
    name = "Pantalla de inicio",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}

