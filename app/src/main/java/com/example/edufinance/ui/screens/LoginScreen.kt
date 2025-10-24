package com.example.edufinance.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufinance.R
import com.example.edufinance.ui.utils.isValidEmail
import com.example.edufinance.ui.utils.isValidPassword

// Pantalla de inicio de sesión donde el usuario introduce su correo y contraseña
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,       // Acción a ejecutar cuando el inicio de sesión sea exitoso
    onNavigateToRegister: () -> Unit, // Acción para ir a la pantalla de registro
    onBackClick: () -> Unit           // Acción para volver atrás
) {
    // Variables de estado que almacenan el correo y la contraseña ingresados
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Controla si la contraseña se muestra u oculta
    var passwordVisible by remember { mutableStateOf(false) }

    // Variables para mostrar mensajes de error si los campos no son válidos
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // Contenedor principal de la interfaz
    Column(
        modifier = Modifier
            .fillMaxSize()                         // Ocupa todo el espacio de la pantalla
            .padding(24.dp),                       // Márgenes interiores
        horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos
        verticalArrangement = Arrangement.Center            // Centra verticalmente el contenido
    ) {
        // Logo de la aplicación
        Image(
            painter = painterResource(id = R.drawable.logo_app),
            contentDescription = "Logo",           // Descripción para accesibilidad
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Nombre de la aplicación en la parte superior
        Text("EduFinance", style = MaterialTheme.typography.headlineMedium, fontSize = 28.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Campo para ingresar el correo electrónico
        OutlinedTextField(
            value = email,                                  // Valor actual del campo
            onValueChange = {
                email = it                                  // Actualiza el valor del texto
                emailError = null                           // Elimina mensaje de error al escribir
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Correo electrónico") },          // Etiqueta del campo
            isError = emailError != null,                   // Muestra borde rojo si hay error
            singleLine = true,                              // Evita saltos de línea
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email) // Teclado de tipo email
        )

        // Mensaje de error bajo el campo de correo
        if (emailError != null) {
            Text(
                text = emailError!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para ingresar la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it                               // Actualiza la contraseña escrita
                passwordError = null                        // Limpia el error al escribir
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Contraseña") },                 // Etiqueta del campo
            isError = passwordError != null,                // Muestra borde rojo si hay error
            singleLine = true,
            // Controla si el texto se muestra o se oculta
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                // Ícono que alterna la visibilidad de la contraseña
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                val description = if (passwordVisible)
                    "Ocultar contraseña"
                else
                    "Mostrar contraseña"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            }
        )

        // Mensaje de error bajo el campo de contraseña
        if (passwordError != null) {
            Text(
                text = passwordError!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón para iniciar sesión
        Button(
            onClick = {
                // Valida los datos ingresados usando funciones auxiliares
                val validEmail = isValidEmail(email)
                val validPassword = isValidPassword(password)

                // Si los datos no son válidos, muestra mensajes de error
                if (!validEmail) emailError = "Correo inválido"
                if (!validPassword) passwordError =
                    "Mínimo 8 caracteres, mayúsculas, minúsculas y símbolo"

                // Si ambos datos son correctos, ejecuta la acción de éxito
                if (validEmail && validPassword) {
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión") // Texto dentro del botón
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón de texto para ir a la pantalla de registro
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón de texto para regresar a la pantalla anterior
        TextButton(onClick = onBackClick) {
            Text("Volver")
        }
    }
}











