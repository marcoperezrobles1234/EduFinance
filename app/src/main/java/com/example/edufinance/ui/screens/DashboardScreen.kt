package com.example.edufinance.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.edufinance.ui.viewmodel.DashboardViewModel

// Pantalla principal del Dashboard financiero donde se muestra el saldo total y permite agregar dinero
@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {

    // Variable de estado para almacenar el texto ingresado en el campo de monto
    var monto by remember { mutableStateOf("") }

    // Estructura principal de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()            // Ocupa todo el espacio disponible
            .padding(16.dp),          // Margen interior de 16dp
        horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente
    ) {
        // Título de la sección
        Text("Resumen Financiero", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Subtítulo que indica el propósito del valor mostrado
        Text("Saldo Total Disponible", style = MaterialTheme.typography.titleLarge)

        // Muestra el saldo total actual obtenido del ViewModel, formateado a dos decimales
        Text(
            "$${String.format("%.2f", viewModel.saldoTotal)}",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de texto para ingresar una cantidad a agregar al saldo
        OutlinedTextField(
            value = monto,                        // Valor actual del campo
            onValueChange = { monto = it },       // Actualiza el valor al escribir
            label = { Text("Agregar monto") }     // Etiqueta visible en el campo
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar el monto ingresado al saldo total
        Button(
            onClick = {
                val valor = monto.toDoubleOrNull()       // Convierte el texto a número
                if (valor != null && valor > 0)          // Verifica que el valor sea válido y positivo
                    viewModel.agregarSaldo(valor)        // Llama al ViewModel para actualizar el saldo
                monto = ""                               // Limpia el campo de texto tras agregar
            }
        ) {
            Text("Agregar a saldo") // Texto dentro del botón
        }
    }
}



