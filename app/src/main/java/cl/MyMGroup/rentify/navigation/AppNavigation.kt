package cl.MyMGroup.rentify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.MyMGroup.rentify.view.CarritoScreen
import cl.MyMGroup.rentify.view.HomeScreen
import cl.MyMGroup.rentify.view.LoginScreen
import cl.MyMGroup.rentify.view.RegistroScreen
import cl.MyMGroup.rentify.view.DecoracionFloralScreen
import androidx.navigation.compose.NavHost
import cl.MyMGroup.rentify.view.AmplificacionYPantallas
import cl.MyMGroup.rentify.view.BanqueteriaScreen
import cl.MyMGroup.rentify.view.CarpasScreen
import cl.MyMGroup.rentify.view.MobiliarioScreen
import cl.MyMGroup.rentify.view.ParcelasScreen
import cl.MyMGroup.rentify.controller.CartViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true; }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("registro")
                }
            )
        }
        composable("registro") {
            RegistroScreen(
                onRegistroSuccess = {
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable("home") { HomeScreen(navController) }
        composable("carrito") {
            CarritoScreen(
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("decoracionFloral") { DecoracionFloralScreen(navController) }
        composable("carpas") { CarpasScreen(navController) }
        composable("parcelas") { ParcelasScreen(navController) }
        composable("banqueteria") { BanqueteriaScreen(navController) }
        composable("mobiliario") { MobiliarioScreen(navController) }
        composable("amplificacion") { AmplificacionYPantallas(navController) }

    }

}

