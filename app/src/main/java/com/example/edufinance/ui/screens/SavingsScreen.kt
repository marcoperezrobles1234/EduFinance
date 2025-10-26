package com.example.edufinance.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Ahorros",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta que muestra el total ahorrado
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Ahorrado",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$${String.format("%.2f", ahorroActual)}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ---- AGREGAR A AHORRO ----
        OutlinedTextField(
            value = montoAhorro,
            onValueChange = { montoAhorro = it },
            label = { Text("Monto a transferir a ahorro") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val monto = montoAhorro.toDoubleOrNull() ?: 0.0
                if (monto > 0 && monto <= saldoDisponible) {
                    viewModel.registrarGasto("Ahorro", monto)
                    mensajeLocal = "Se han transferido $${String.format("%.2f", monto)} al ahorro"
                    montoAhorro = ""
                } else if (monto > saldoDisponible) {
                    mensajeLocal = "Saldo insuficiente para transferir."
                } else {
                    mensajeLocal = "Ingrese un monto válido."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar en Ahorros")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ---- RETIRAR DE AHORRO ----
        OutlinedTextField(
            value = montoRetiro,
            onValueChange = { montoRetiro = it },
            label = { Text("Monto a retirar del ahorro") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val monto = montoRetiro.toDoubleOrNull() ?: 0.0
                viewModel.retirarDeAhorro(monto)
                mensajeLocal = viewModel.mensaje
                montoRetiro = ""
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retirar de Ahorros")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Muestra mensaje informativo
        if (mensajeLocal.isNotEmpty()) {
            Text(
                text = mensajeLocal,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
//Actualizado (2)