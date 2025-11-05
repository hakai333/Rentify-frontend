package cl.MyMGroup.rentify.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.MyMGroup.rentify.view.CarritoScreen
import cl.MyMGroup.rentify.view.HomeScreen
import cl.MyMGroup.rentify.view.LoginScreen
import cl.MyMGroup.rentify.view.RegistroScreen
import cl.MyMGroup.rentify.view.DecoracionFloralScreen
import androidx.navigation.compose.NavHost
import cl.MyMGroup.rentify.data.dao.UsuarioDao
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.repository.UsuarioRepository
import cl.MyMGroup.rentify.ui.theme.RentifyTheme
import cl.MyMGroup.rentify.view.AmplificacionYPantallas
import cl.MyMGroup.rentify.view.BanqueteriaScreen
import cl.MyMGroup.rentify.view.CarpasScreen
import cl.MyMGroup.rentify.view.MobiliarioScreen
import cl.MyMGroup.rentify.view.ParcelasScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current;
    val database = remember {
        RentifyDataBase.genInstance(context)
    }


    val repository = remember { UsuarioRepository(database.usuarioDao()) }

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
        composable("carrito") { CarritoScreen(navController) }
        composable("decoracionFloral") { DecoracionFloralScreen(navController) }
        composable("carpas") { CarpasScreen(navController) }
        composable("parcelas") { ParcelasScreen(navController) }
        composable("banqueteria") { BanqueteriaScreen(navController) }
        composable("mobiliario") { MobiliarioScreen(navController) }
        composable("amplificacion") { AmplificacionYPantallas(navController) }

    }

}

