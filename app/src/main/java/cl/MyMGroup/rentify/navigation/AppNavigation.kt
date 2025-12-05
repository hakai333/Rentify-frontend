package cl.MyMGroup.rentify.navigation

import CotizacionScreen
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.MyMGroup.rentify.view.*
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import cl.MyMGroup.rentify.controller.*
import cl.MyMGroup.rentify.data.api.ApiService
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.network.RetrofitProvider
import cl.MyMGroup.rentify.data.repository.PedidoRepository

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val apiService: ApiService = RetrofitProvider.apiService

    val db = RentifyDataBase.getInstance(context)
    val cartDao = db.cartDao()
    val pedidoRepository = PedidoRepository(apiService)

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(apiService)
    )
    val pedidoViewModel: PedidoViewModel = viewModel(
        factory = PedidoViewModelFactory(pedidoRepository)
    )
    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(cartDao, pedidoRepository)
    )

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                loginViewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
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

        // HomeScreen con QR opcional
        composable(
            route = "home?qr={qr}",
            arguments = listOf(
                navArgument("qr") {
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val qrValue: String? = backStackEntry.arguments?.getString("qr") // <-- explícitamente nullable
            HomeScreen(navController = navController, qrValue)
        }


        composable("carrito") {
            CarritoScreen(
                cartViewModel = cartViewModel,
                loginViewModel = loginViewModel,
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }

        composable("cotizacionScreen") {
            CotizacionScreen(
                navController = navController,
                pedidoViewModel = pedidoViewModel,
                loginViewModel = loginViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // Resto de pantallas que ya funcionan
        composable("decoracionFloral") { DecoracionFloralScreen(navController, cartViewModel) }
        composable("carpas") { CarpasScreen(navController, cartViewModel) }
        composable("banqueteria") { BanqueteriaScreen(navController, cartViewModel) }
        composable("mobiliario") { MobiliarioScreen(navController, cartViewModel) }
        composable("amplificacion") { AmplificacionYPantallasScreen(navController, cartViewModel) }

        // PacksDestacadosScreen
        composable("packsDestacados") {
            val viewModel: PacksDestacadosViewModel = viewModel(
                factory = PacksDestacadosViewModelFactory(context.applicationContext as Application)
            )
            PacksDestacadosScreen(navController, viewModel)
        }

        composable("qrScanner") {
            QrScannerScreen(
                onQrScanned = { qr ->
                    navController.popBackStack() // primero cerramos el scanner
                    if (qr == "PACKS_DESTACADOS") {
                        navController.navigate("packsDestacados")
                    }
                },
                onClose = {
                    navController.popBackStack() // cierra scanner si presiona cerrar
                }
            )
        }

    }
}
