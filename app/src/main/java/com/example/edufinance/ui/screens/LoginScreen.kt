package com.example.edufinance.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufinance.R
import com.example.edufinance.ui.utils.isValidEmail
import com.example.edufinance.ui.utils.isValidPassword

// Pantalla de inicio de sesión donde el usuario introduce su correo y contraseña

@OptIn(ExperimentalMaterial3Api::class)
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFE082),// dorado claro
                        Color(0xFFFFF8E1), // blanco-dorado muy suave
                        Color(0xFFF9F9F9),
                        Color(0xFFB2DFDB),  // verde muy suave
                        Color(0xFF007F5F),
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "EduFinance",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF007F5F)
            )
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = null },
                label = { Text("Correo electrónico") },
                isError = emailError != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            if (emailError != null) {
                Text(emailError!!, color = Color.Red, fontSize = 12.sp)
            }
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordError = null },
                label = { Text("Contraseña") },
                isError = passwordError != null,
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(icon, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (passwordError != null) {
                Text(passwordError!!, color = Color.Red, fontSize = 12.sp)
            }
            Spacer(Modifier.height(20.dp))

            // Botón dorado brillante
            Button(
                onClick = {
                    val validEmail = isValidEmail(email)
                    val validPassword = isValidPassword(password)
                    if (!validEmail) emailError = "Correo inválido"
                    if (!validPassword) passwordError = "Mínimo 8 caracteres, mayúsculas, minúsculas y símbolo"
                    if (validEmail && validPassword) onLoginSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .shadow(6.dp, shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFFC9A227), Color(0xFFFFD166))
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Iniciar sesión", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(12.dp))
            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Regístrate", color = Color(0xFF007F5F))
            }
            Spacer(Modifier.height(12.dp))
            TextButton(onClick = onBackClick) {
                Text("Volver", color = Color(0xFF007F5F))
            }
        }
    }
}











