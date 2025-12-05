package cl.MyMGroup.rentify.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import cl.MyMGroup.rentify.ui.camera.CameraPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerScreen(
    onQrScanned: (String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier // ✅ parámetro opcional
) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Escanear QR") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Cerrar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = modifier
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (hasCameraPermission) {
                CameraPreview(
                    onQrScanned = { value ->
                        onQrScanned(value)
                    }
                )
            } else {
                Text("Se necesita permiso de cámara para escanear QR")
            }
        }
    }
}
