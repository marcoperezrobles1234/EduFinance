package com.example.edufinance.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edufinance.ui.viewmodel.RemindersViewModel
import com.example.edufinance.ui.viewmodel.ReminderUi
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RemindersScreen(viewModel: RemindersViewModel) {

    var nuevo by remember { mutableStateOf("") }
    var showEdit by remember { mutableStateOf(false) }
    var editing: ReminderUi? by remember { mutableStateOf(null) }
    val lista = viewModel.reminders
    val mensaje = viewModel.mensaje

    val sdf = remember { SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // Título
        item {
            Text(
                "Recordatorios",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Input + botón para agregar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = nuevo,
                    onValueChange = { nuevo = it },
                    label = { Text("Escribe un recordatorio") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                Button(
                    onClick = {
                        if (nuevo.isNotBlank()) {
                            viewModel.addReminder(nuevo)
                            nuevo = ""
                        }
                    }
                ) { Text("Agregar") }
            }
        }

        // Lista de recordatorios
        if (lista.isEmpty()) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Aún no hay recordatorios",
                    style = MaterialTheme.typography.bodyMedium
                )
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

        // Mensajes informativos
        if (mensaje.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    mensaje.trim(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Tarjeta con conteo (opcional)
        item {
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
                    Text(
                        text = "Total de recordatorios",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "${lista.size}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    // Diálogo de edición
    if (showEdit) {
        var editText by remember(editing) { mutableStateOf(editing?.text.orEmpty()) }

        AlertDialog(
            onDismissRequest = { showEdit = false },
            title = { Text("Editar recordatorio") },
            text = {
                OutlinedTextField(
                    value = editText,
                    onValueChange = { editText = it },
                    singleLine = false,
                    maxLines = 5,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Modificar texto del recordatorio") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    editing?.let { viewModel.updateReminder(it.id, editText) }
                    showEdit = false
                }) { Text("Guardar") }
            },
            dismissButton = {
                TextButton(onClick = { showEdit = false }) { Text("Cancelar") }
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
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = dateText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row {
                TextButton(onClick = onEdit) { Text("Editar") }
                TextButton(onClick = onDelete) { Text("Eliminar") }
            }
        }
    }
}
//Actualizado (3)

