package com.example.edufinance.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.material3.TextFieldDefaults



@OptIn(ExperimentalMaterial3Api::class)
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Logo EduFinance",
                modifier = Modifier.size(100.dp)
            )

            Spacer(Modifier.height(12.dp))
            Text(
                "Crear cuenta",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF007F5F)
            )
            Spacer(Modifier.height(24.dp))

            // === Campo Nombre ===
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre completo", color = Color(0xFF007F5F)) },
                textStyle = LocalTextStyle.current.copy(color = Color(0xFF007F5F)),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF007F5F),
                    unfocusedIndicatorColor = Color(0xFF007F5F),
                    cursorColor = Color(0xFF007F5F),
                    focusedLabelColor = Color(0xFF007F5F),
                    unfocusedLabelColor = Color(0xFF007F5F)
                )
            )

            Spacer(Modifier.height(12.dp))

            // === Campo Email ===
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = null },
                label = { Text("Correo electrónico", color = Color(0xFF007F5F)) },
                isError = emailError != null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = LocalTextStyle.current.copy(color = Color(0xFF007F5F)),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF007F5F),
                    unfocusedIndicatorColor = Color(0xFF007F5F),
                    cursorColor = Color(0xFF007F5F),
                    focusedLabelColor = Color(0xFF007F5F),
                    unfocusedLabelColor = Color(0xFF007F5F)
                )
            )
            if (emailError != null)
                Text(emailError!!, color = Color.Red, fontSize = 12.sp)

            Spacer(Modifier.height(12.dp))

            // === Campo Contraseña ===
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordError = null },
                label = { Text("Contraseña", color = Color(0xFF007F5F)) },
                isError = passwordError != null,
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(icon, contentDescription = null, tint = Color(0xFF007F5F))
                    }
                },
                textStyle = LocalTextStyle.current.copy(color = Color(0xFF007F5F)),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF007F5F),
                    unfocusedIndicatorColor = Color(0xFF007F5F),
                    focusedLabelColor = Color(0xFF007F5F),
                    unfocusedLabelColor = Color(0xFF007F5F),
                    cursorColor = Color(0xFF007F5F)
                )
            )
            if (passwordError != null)
                Text(passwordError!!, color = Color.Red, fontSize = 12.sp)

            Spacer(Modifier.height(24.dp))

            // === Botón Dorado ===
            Button(
                onClick = {
                    val validEmail = isValidEmail(email)
                    val validPassword = isValidPassword(password)

                    if (!validEmail) emailError = "Correo inválido"
                    if (!validPassword) passwordError = "Mínimo 8 caracteres, mayúsculas, minúsculas y símbolo"

                    if (validEmail && validPassword) {
                        onRegisterSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFFD4AF37), Color(0xFFFFE47A))
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Registrarse",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            TextButton(onClick = onNavigateToLogin) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = Color(0xFF007F5F))
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onBackClick) {
                Text("Volver", color = Color(0xFF007F5F))
            }
        }
    }
}




