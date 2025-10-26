package com.example.edufinance.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufinance.ui.viewmodel.DashboardViewModel

// Pantalla que permite al usuario registrar su presupuesto semanal por categorías
@Composable
fun BudgetScreen(viewModel: DashboardViewModel) {

    // Variables de estado para cada categoría del presupuesto (se actualizan con el texto ingresado)
    var alimentos by remember { mutableStateOf("") }
    var transporte by remember { mutableStateOf("") }
    var libros by remember { mutableStateOf("") }
    var ocio by remember { mutableStateOf("") }
    var ahorro by remember { mutableStateOf("") }

    // Variables que obtienen información del ViewModel
    val mensaje = viewModel.mensaje        // Mensaje informativo o de confirmación
    val saldoTotal = viewModel.saldoTotal  // Saldo restante total calculado en el ViewModel

    // Lista con los nombres de las categorías y sus valores asociados
    val campos = listOf(
        Pair("Alimentos", Pair(alimentos) { nuevoValor: String -> alimentos = nuevoValor }),
        Pair("Transporte", Pair(transporte) { nuevoValor: String -> transporte = nuevoValor }),
        Pair("Libros", Pair(libros) { nuevoValor: String -> libros = nuevoValor }),
        Pair("Ocio", Pair(ocio) { nuevoValor: String -> ocio = nuevoValor }),
        Pair("Ahorro", Pair(ahorro) { nuevoValor: String -> ahorro = nuevoValor })
    )

    // Lista desplazable verticalmente que muestra los campos y botones
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // Título de la pantalla
        item {
            Text(
                "Gasto Semanal",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Recorre la lista de categorías y genera un campo de texto + botón por cada una
        items(campos.size) { index ->
            val (label, campo) = campos[index]
            val valor = campo.first           // Valor actual del campo de texto
            val onValueChange = campo.second  // Función que actualiza el texto

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Campo de texto para ingresar el monto de la categoría
                OutlinedTextField(
                    value = valor,
                    onValueChange = onValueChange,
                    label = { Text(label) },      // Muestra el nombre de la categoría
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                // Botón que registra el gasto al presionarlo
                Button(
                    onClick = {
                        val monto = valor.toDoubleOrNull() ?: 0.0  // Convierte el texto a número
                        if (monto > 0) {                           // Solo si el monto es válido
                            viewModel.registrarGasto(label, monto) // Registra el gasto en el ViewModel
                            onValueChange("")                      // Limpia el campo tras registrar
                        }
                    },
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text("Aceptar")
                }
            }
        }

        // Sección inferior con el saldo restante y mensajes informativos
        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Muestra un mensaje informativo si existe (por ejemplo, confirmación)
            if (mensaje.isNotEmpty()) {
                Text(
                    mensaje.trim(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Tarjeta que muestra el saldo restante calculado
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Texto descriptivo
                    Text(
                        text = "Saldo restante",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    // Muestra el saldo formateado con dos decimales
                    Text(
                        text = "$${String.format("%.2f", saldoTotal)}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}


















