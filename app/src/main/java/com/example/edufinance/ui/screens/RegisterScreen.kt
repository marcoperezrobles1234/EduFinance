package com.example.edufinance.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,    // Acción al registrarse correctamente
    onNavigateToLogin: () -> Unit,    // Acción para ir a la pantalla de inicio de sesión
    onBackClick: () -> Unit           // Acción para volver a la pantalla anterior
) {
    // Variables reactivas que almacenan el texto ingresado por el usuario
    var name by remember { mutableStateOf("") }          // Nombre completo
    var email by remember { mutableStateOf("") }         // Correo electrónico
    var password by remember { mutableStateOf("") }      // Contraseña
    var passwordVisible by remember { mutableStateOf(false) } // Controla si la contraseña se muestra o se oculta

    // Variables para mostrar mensajes de error si los datos son inválidos
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // Contenedor principal de la pantalla de registro
    Column(
        modifier = Modifier
            .fillMaxSize()               // Ocupa toda la pantalla
            .padding(24.dp),             // Margen interno
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo de la aplicación
        Image(
            painter = painterResource(id = R.drawable.logo_app),
            contentDescription = "Logo",
            modifier = Modifier.size(90.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Título principal
        Text("Crear cuenta", style = MaterialTheme.typography.headlineMedium, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Campo para ingresar el nombre del usuario
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para ingresar el correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null // Limpia el error al modificar el texto
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null, // Marca error si la variable tiene texto
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        // Muestra mensaje de error si el correo no es válido
        if (emailError != null) Text(emailError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para ingresar la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null // Limpia el error al escribir
            },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError != null, // Indica visualmente si hay un error
            singleLine = true,
            // Si passwordVisible es true, muestra el texto; si no, lo oculta con asteriscos
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                // Ícono que alterna la visibilidad de la contraseña
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val desc = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = desc)
                }
            }
        )
        // Muestra mensaje de error si la contraseña no cumple los requisitos
        if (passwordError != null) Text(passwordError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Botón principal para registrar al usuario
        Button(
            onClick = {
                // Validaciones usando funciones auxiliares
                val validEmail = isValidEmail(email)
                val validPassword = isValidPassword(password)

                // Muestra los errores correspondientes si algo está mal
                if (!validEmail) emailError = "Correo inválido"
                if (!validPassword) passwordError = "Mínimo 8 caracteres, mayúsculas, minúsculas y símbolo"

                // Si ambos datos son válidos, ejecuta la acción de registro exitoso
                if (validEmail && validPassword) {
                    onRegisterSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón de texto para ir a la pantalla de inicio de sesión
        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón de texto para volver a la pantalla anterior
        TextButton(onClick = onBackClick) {
            Text("Volver")
        }
    }
}








