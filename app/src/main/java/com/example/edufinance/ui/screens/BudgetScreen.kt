package com.example.edufinance.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun BudgetScreen(viewModel: DashboardViewModel) {
    var alimentos by remember { mutableStateOf("") }
    var transporte by remember { mutableStateOf("") }
    var libros by remember { mutableStateOf("") }
    var ocio by remember { mutableStateOf("") }
    var ahorro by remember { mutableStateOf("") }

    val mensaje = viewModel.mensaje
    val saldoTotal = viewModel.saldoTotal

    val campos = listOf(
        Pair("Alimentos", Pair(alimentos) { nuevo: String -> alimentos = nuevo }),
        Pair("Transporte", Pair(transporte) { nuevo: String -> transporte = nuevo }),
        Pair("Libros", Pair(libros) { nuevo: String -> libros = nuevo }),
        Pair("Ocio", Pair(ocio) { nuevo: String -> ocio = nuevo }),
        Pair("Ahorro", Pair(ahorro) { nuevo: String -> ahorro = nuevo })
    )

    // Fondo blanco degradado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFF9F9F9))
                )
            )
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            item {
                Text(
                    "Gasto Semanal",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007F5F)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(campos.size) { index ->
                val (label, campo) = campos[index]
                val valor = campo.first
                val onValueChange = campo.second

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                            value = valor,
                            onValueChange = onValueChange,
                            label = { Text(label, color = Color(0xFF007F5F)) },
                            textStyle = LocalTextStyle.current.copy(color = Color(0xFF007F5F)),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 12.dp),

                    )

                    // Botón brillante dorado
                    Button(
                        onClick = {
                            val monto = valor.toDoubleOrNull() ?: 0.0
                            if (monto > 0) {
                                viewModel.registrarGasto(label, monto)
                                onValueChange("")
                            }
                        },
                        modifier = Modifier
                            .height(48.dp)
                            .weight(0.5f)
                            .shadow(6.dp, shape = RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
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
                                text = "Aceptar",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                if (mensaje.isNotEmpty()) {
                    Text(
                        mensaje.trim(),
                        color = Color(0xFF007F5F),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Tarjeta elegante
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(24.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, Color(0xFFC9A227)) // Borde dorado
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Saldo restante",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF007F5F)
                        )
                        Text(
                            text = "$${String.format("%.2f", saldoTotal)}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFC9A227)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}



















