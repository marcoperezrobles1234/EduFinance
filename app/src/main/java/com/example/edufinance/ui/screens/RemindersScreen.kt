package com.example.edufinance.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufinance.ui.viewmodel.RemindersViewModel
import com.example.edufinance.ui.viewmodel.ReminderUi
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(viewModel: RemindersViewModel) {

    var nuevo by remember { mutableStateOf("") }
    var showEdit by remember { mutableStateOf(false) }
    var editing: ReminderUi? by remember { mutableStateOf(null) }
    val lista = viewModel.reminders
    val mensaje = viewModel.mensaje

    val sdf = remember { SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFF9F9F9))
                )
            )
            .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🟢 Título
            Text(
                "Recordatorios",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF007F5F)
            )
            Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            // 🟢 Input y botón agregar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = nuevo,
                        onValueChange = { nuevo = it },
                        label = { Text("Escribe un recordatorio", color = Color(0xFF007F5F)) },
                        textStyle = LocalTextStyle.current.copy(color = Color(0xFF007F5F)),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFF007F5F),
                            unfocusedIndicatorColor = Color(0xFF007F5F),
                            cursorColor = Color(0xFF007F5F),
                            focusedLabelColor = Color(0xFF007F5F),
                            unfocusedLabelColor = Color(0xFF007F5F)
                        )
                    )

                    // ✨ Botón dorado brillante
                    Button(
                        onClick = {
                            if (nuevo.isNotBlank()) {
                                viewModel.addReminder(nuevo)
                                nuevo = ""
                            }
                        },
                        modifier = Modifier
                            .height(56.dp)
                            .shadow(5.dp, RoundedCornerShape(14.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues()
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFFC9A227), Color(0xFFFFD166))
                                    ),
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text("Agregar", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            // 🟢 Lista de recordatorios
            if (lista.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Aún no hay recordatorios", color = Color.Gray)
                }
            } else {
                items(lista, key = { it.id }) { r ->
                    ReminderRow(
                        reminder = r,
                        dateText = sdf.format(Date(r.createdAt)),
                        onEdit = {
                            editing = r
                            showEdit = true
                        },
                        onDelete = { viewModel.deleteReminder(r.id) }
                    )
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }

            // 🟢 Mensaje del sistema
            if (mensaje.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        mensaje.trim(),
                        color = Color(0xFF007F5F),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // 🟢 Tarjeta con conteo
            item {
                Spacer(modifier = Modifier.height(20.dp))
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
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Total de recordatorios",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF007F5F),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${lista.size}",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF007F5F),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }

    // 🟢 Diálogo de edición
    if (showEdit) {
        var editText by remember(editing) { mutableStateOf(editing?.text.orEmpty()) }

        AlertDialog(
            onDismissRequest = { showEdit = false },
            title = { Text("Editar recordatorio", color = Color(0xFF007F5F)) },
            text = {
                OutlinedTextField(
                    value = editText,
                    onValueChange = { editText = it },
                    singleLine = false,
                    maxLines = 5,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Modificar texto del recordatorio") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFF007F5F),
                        unfocusedIndicatorColor = Color(0xFF007F5F),
                        cursorColor = Color(0xFF007F5F),
                        focusedLabelColor = Color(0xFF007F5F),
                        unfocusedLabelColor = Color(0xFF007F5F)
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    editing?.let { viewModel.updateReminder(it.id, editText) }
                    showEdit = false
                }) { Text("Guardar", color = Color(0xFF007F5F)) }
            },
            dismissButton = {
                TextButton(onClick = { showEdit = false }) { Text("Cancelar", color = Color.Gray) }
            }
        )
    }
}

@Composable
private fun ReminderRow(
    reminder: ReminderUi,
    dateText: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = reminder.text,
                    fontSize = 16.sp,
                    color = Color(0xFF007F5F),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = dateText,
                    fontSize = 12.sp,
                    color = Color(0xFF5E5E5E)
                )
            }
            Row {
                TextButton(onClick = onEdit) { Text("Editar", color = Color(0xFF007F5F)) }
                TextButton(onClick = onDelete) { Text("Eliminar", color = Color(0xFFB00020)) }
            }
        }
    }
}
