package cl.MyMGroup.rentify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.MyMGroup.rentify.ui.screens.home.HomeScreen
import cl.MyMGroup.rentify.ui.screens.login.LoginScreen
import cl.MyMGroup.rentify.ui.screens.registro.RegistroScreen
import cl.MyMGroup.rentify.ui.screens.venta.VentaScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen() }
        composable("registro") { RegistroScreen() }
        composable("home") { HomeScreen() }
        composable("venta") { VentaScreen() }
    }

}