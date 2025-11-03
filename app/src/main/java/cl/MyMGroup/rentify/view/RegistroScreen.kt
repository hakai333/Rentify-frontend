package cl.MyMGroup.rentify.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.MyMGroup.rentify.controller.RegistroViewModel
import cl.MyMGroup.rentify.controller.RegistroState

@Composable
fun RegistroScreen(
    onRegistroSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegistroViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }
    var localErrorMessage by remember { mutableStateOf("") }

    val registroState by viewModel.registroState.collectAsState()

    // Manejar el estado del registro
    LaunchedEffect(registroState) {
        when (registroState) {
            is RegistroState.Success -> {
                onRegistroSuccess()
                viewModel.resetState()
            }
            is RegistroState.Error -> {
                localErrorMessage = (registroState as RegistroState.Error).message
            }
            else -> {}
        }
    }

    // Función para validar y hacer registro
    fun performRegistro() {
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            localErrorMessage = "Por favor completa todos los campos"
            return
        }
        if (password != confirmarPassword) {
            localErrorMessage = "Las contraseñas no coinciden"
            return
        }
        if (password.length < 6) {
            localErrorMessage = "La contraseña debe tener al menos 6 caracteres"
            return
        }
        localErrorMessage = ""
        viewModel.registrarUsuario(nombre, email, password, confirmarPassword)
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

        // Campo Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                if (localErrorMessage.isNotEmpty()) localErrorMessage = ""
            },
            label = { Text("Nombre completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

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
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Registro
        Button(
            onClick = { performRegistro() },
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

        // Texto para volver al login
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

