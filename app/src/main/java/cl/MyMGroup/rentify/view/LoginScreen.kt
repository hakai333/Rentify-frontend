package cl.MyMGroup.rentify.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(){
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(
        // Propiedades
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(10.dp),

    ) {
        //Contenedores
        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            label = { Text ("Usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text ("Contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {


            },
            //Bordes del boton
            shape = RoundedCornerShape(5.dp),
            //Modificamos la altura a 55dp y llenamosMaximoAncho
            modifier = Modifier.height(55.dp).fillMaxWidth()
        ) {
            Text("Ingresar")
        }
    }
}

@Preview(
    name = "Pantalla login",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun PreviewLogin() {
    LoginScreen()
}