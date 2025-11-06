package cl.MyMGroup.rentify.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.MyMGroup.rentify.controller.RegistroViewModel
import cl.MyMGroup.rentify.controller.RegistroState
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.data.repository.UsuarioRepository
import kotlinx.coroutines.delay

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color


@Composable
fun RegistroScreen(
    onRegistroSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    // ✅ Ya no se usa Room, ni Factory, ni Repository
    val viewModel: RegistroViewModel = viewModel()
    val registroState by viewModel.registroState.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("" ) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    var localErrorMessage by remember { mutableStateOf("") }
    var localMessageColor by remember { mutableStateOf(Color.Black) }






    // Manejar el estado del registro
    LaunchedEffect(registroState) {
        when (registroState) {
            is RegistroState.Success -> {
                // Asigna primero mensaje y color
                localErrorMessage = (registroState as RegistroState.Success).message
                localMessageColor = Color(0xFF4CAF50) // Verde éxito

                // Opcional: esperar 1.5s antes de limpiar
                delay(1500)

                viewModel.resetState()
                onRegistroSuccess()
            }
            is RegistroState.Error -> {
                localErrorMessage = (registroState as RegistroState.Error).message
                localMessageColor = Color.Red
            }
            else -> {
                localErrorMessage = ""
            }
        }
    }




    val isLoading = registroState is RegistroState.Loading

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Título
        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )


        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                if (localErrorMessage.isNotEmpty()) localErrorMessage = ""
            },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )


        OutlinedTextField(
            value = apellido,
            onValueChange = {
                apellido = it
                if (localErrorMessage.isNotEmpty()) localErrorMessage = ""
            },
            label = { Text("Apellido") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )



        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (localErrorMessage.isNotEmpty()) localErrorMessage = ""
            },
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        // Campo Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (localErrorMessage.isNotEmpty()) localErrorMessage = ""
            },
            label = { Text("Contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            enabled = !isLoading
        )


        // Campo Confirmar Contraseña
        OutlinedTextField(
            value = confirmarPassword,
            onValueChange = {
                confirmarPassword = it
                if (localErrorMessage.isNotEmpty()) localErrorMessage = ""
            },
            label = { Text("Confirmar contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            enabled = !isLoading
        )

        // Mostrar mensaje de error
        if (localErrorMessage.isNotEmpty()) {
            Text(
                text = localErrorMessage,
                color = localMessageColor,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Registro
        Button(
            onClick = {
                viewModel.register(
                    nombre,
                    apellido,
                    email,
                    password,
                    confirmarPassword
                )
                      },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Registrarse")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto para navegar al login
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¿Ya tienes cuenta? ",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            ClickableText(
                text = AnnotatedString("Iniciar sesión"),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                onClick = { onNavigateToLogin() }
            )
        }






    }
}

