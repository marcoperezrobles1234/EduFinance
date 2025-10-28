package com.example.edufinance.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_app),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "EduFinance",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF007F5F)
        )

        Spacer(Modifier.height(40.dp))

        // Botón dorado brillante - Iniciar Sesión
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .shadow(6.dp, RoundedCornerShape(12.dp)),
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
                Text("Iniciar Sesión", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(12.dp))

        // Botón dorado brillante - Registrarse
        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .shadow(6.dp, RoundedCornerShape(12.dp)),
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
                Text("Registrarse", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}














