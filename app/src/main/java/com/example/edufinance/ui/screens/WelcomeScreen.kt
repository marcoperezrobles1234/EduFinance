package com.example.edufinance.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufinance.R

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,        // Permite aplicar modificaciones externas a la pantalla
    onLoginClick: () -> Unit,            // Acción al presionar el botón de inicio de sesión
    onRegisterClick: () -> Unit          // Acción al presionar el botón de registro
) {
    // Contenedor principal de la pantalla, centrando los elementos
    Column(
        modifier = modifier
            .fillMaxSize()               // Ocupa toda la pantalla
            .padding(24.dp),             // Márgenes internos
        horizontalAlignment = Alignment.CenterHorizontally, // Centra elementos horizontalmente
        verticalArrangement = Arrangement.Center           // Centra elementos verticalmente
    ) {
        // Imagen del logo de la app
        Image(
            painter = painterResource(id = R.drawable.logo_app),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)  // Tamaño de la imagen
        )

        Spacer(Modifier.height(16.dp))       // Espacio entre el logo y el título

        // Nombre de la app en grande y en negrita
        Text(
            text = "EduFinance",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(40.dp))       // Espacio antes de los botones

        // Botón para ir a la pantalla de login
        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()  // Botón ocupa todo el ancho disponible
        ) {
            Text("Iniciar Sesión")            // Texto dentro del botón
        }

        Spacer(Modifier.height(12.dp))       // Separación entre botones

        // Botón para ir a la pantalla de registro
        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors() // Colores por defecto del tema
        ) {
            Text("Registrarse")                // Texto dentro del botón
        }
    }
}














