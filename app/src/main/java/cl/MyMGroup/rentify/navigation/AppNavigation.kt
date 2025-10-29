package cl.MyMGroup.rentify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.MyMGroup.rentify.view.CarritoScreen
import cl.MyMGroup.rentify.view.HomeScreen
import cl.MyMGroup.rentify.view.LoginScreen
import cl.MyMGroup.rentify.view.RegistroScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen() }
        composable("registro") { RegistroScreen() }
        composable("home") { HomeScreen(navController) }
        composable("carrito") { CarritoScreen() }
    }

}