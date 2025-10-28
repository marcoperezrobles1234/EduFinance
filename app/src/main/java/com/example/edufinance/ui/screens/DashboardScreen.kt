package com.example.edufinance.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufinance.ui.viewmodel.DashboardViewModel

// Pantalla principal del Dashboard financiero donde se muestra el saldo total y permite agregar dinero
@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {

    // Variable de estado para almacenar el texto ingresado en el campo de monto
    var monto by remember { mutableStateOf("") }

    // Estructura principal de la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFF9F9F9))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Resumen Financiero",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF007F5F)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Saldo Total Disponible",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF007F5F)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Tarjeta elegante para el saldo
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, shape = RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, Color(0xFFC9A227))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "$${String.format("%.2f", viewModel.saldoTotal)}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC9A227)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Campo para agregar monto
            OutlinedTextField(
                value = monto,
                onValueChange = { monto = it},
                label = { Text("Agregar monto", color = Color(0xFF007F5F)) },
                textStyle = LocalTextStyle.current.copy(color = Color(0xFF007F5F)),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botón dorado brillante
            Button(
                onClick = {
                    val valor = monto.toDoubleOrNull()
                    if (valor != null && valor > 0) {
                        viewModel.agregarSaldo(valor)
                        monto = ""
                    }
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
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFC9A227), // Dorado oscuro
                                    Color(0xFFFFD166)  // Dorado claro
                                )
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Agregar a saldo",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}