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

@Composable
fun SavingsScreen(viewModel: DashboardViewModel) {
    val ahorroActual = viewModel.weeklyBudget.ahorro      // Monto actual ahorrado
    val saldoDisponible = viewModel.saldoTotal            // Saldo disponible general

    var montoAhorro by remember { mutableStateOf("") }     // Campo para agregar ahorro
    var montoRetiro by remember { mutableStateOf("") }     // Campo para retirar ahorro
    var mensajeLocal by remember { mutableStateOf("") }    // Mensaje de retroalimentación

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Ahorros",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF007F5F)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta total ahorrado
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
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Total Ahorrado",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF007F5F)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "$${String.format("%.2f", ahorroActual)}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC9A227)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ---- AGREGAR A AHORRO ----
        OutlinedTextField(
            value = montoAhorro,
            onValueChange = { montoAhorro = it },
            label = { Text("Monto a transferir a ahorro", color = Color(0xFF007F5F)) },
            textStyle = LocalTextStyle.current.copy(color = Color(0xFF007F5F)),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val monto = montoAhorro.toDoubleOrNull() ?: 0.0
                when {
                    monto <= 0 -> mensajeLocal = "Ingrese un monto válido."
                    monto > saldoDisponible -> mensajeLocal = "Saldo insuficiente para transferir."
                    else -> {
                        viewModel.registrarGasto("Ahorro", monto)
                        mensajeLocal = "Se han transferido $${String.format("%.2f", monto)} al ahorro"
                        montoAhorro = ""
                    }
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
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFFC9A227), Color(0xFFFFD166))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("Guardar en Ahorros", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ---- RETIRAR DE AHORRO ----
        OutlinedTextField(
            value = montoRetiro,
            onValueChange = { montoRetiro = it },
            label = { Text("Monto a retirar del ahorro", color = Color(0xFF007F5F)) },
            textStyle = LocalTextStyle.current.copy(color = Color(0xFF007F5F)),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val monto = montoRetiro.toDoubleOrNull() ?: 0.0
                when {
                    monto <= 0 -> mensajeLocal = "Ingrese un monto válido."
                    monto > ahorroActual -> mensajeLocal = "No hay suficiente ahorro."
                    else -> {
                        viewModel.retirarDeAhorro(monto)
                        mensajeLocal = viewModel.mensaje
                        montoRetiro = ""
                    }
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
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFFC9A227), Color(0xFFFFD166))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("Retirar de Ahorros", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mensaje informativo
        if (mensajeLocal.isNotEmpty()) {
            Text(
                text = mensajeLocal,
                color = Color(0xFF007F5F),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}//Actualizado (2)