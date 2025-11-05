package cl.MyMGroup.rentify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.MyMGroup.rentify.controller.CartViewModel
import cl.MyMGroup.rentify.controller.CartViewModelFactory
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.view.*

@Composable
fun AppNavigation(dataBase: RentifyDataBase) {
    val navController = rememberNavController()
    val cartDao = dataBase.cartDao()


    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(cartDao)
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") { popUpTo("login") { inclusive = true } } },
                onNavigateToRegister = { navController.navigate("registro") }
            )
        }

        composable("registro") {
            RegistroScreen(
                onRegistroSuccess = { navController.navigate("login") { popUpTo("registro") { inclusive = true } } },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }



        composable("carrito") {
            CarritoScreen(
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("home") { HomeScreen(navController) }
        composable("decoracionFloral") { DecoracionFloralScreen(navController) }
        composable("carpas") { CarpasScreen(navController) }
        composable("parcelas") { ParcelasScreen(navController) }
        composable("banqueteria") { BanqueteriaScreen(navController) }
        composable("mobiliario") { MobiliarioScreen(navController) }
        composable("amplificacion") { AmplificacionYPantallas(navController) }
    }
}

