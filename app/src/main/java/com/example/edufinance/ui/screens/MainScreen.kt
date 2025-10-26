package com.example.edufinance.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.edufinance.ui.viewmodel.DashboardViewModel
import com.example.edufinance.ui.viewmodel.RemindersViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel,
    showReminderOnStart: Boolean = false
) {
    // ViewModel para la sección de recordatorios
    val remindersVM: RemindersViewModel = viewModel()

    // Estado para saber qué pantalla está seleccionada
    var selectedItem by rememberSaveable { mutableStateOf("dashboard") }

    // Controla si se muestra el pop-up del recordatorio
    var showGate by rememberSaveable { mutableStateOf(showReminderOnStart) }

    // Obtiene el último recordatorio disponible (si existe)
    val lastReminder = remindersVM.reminders.firstOrNull()

    // Elementos de la barra inferior con su ícono y etiqueta
    val items = listOf(
        "dashboard" to Icons.Default.MonetizationOn,
        "budget" to Icons.Default.AccountBalanceWallet,
        "savings" to Icons.Default.Savings,
        "reminders" to Icons.Default.Notifications,
        "settings" to Icons.Default.Settings
    )

    // Estructura principal con barra inferior
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { (route, icon) ->
                    NavigationBarItem(
                        selected = selectedItem == route,
                        onClick = { selectedItem = route },
                        icon = { Icon(icon, contentDescription = route) },
                        label = {
                            Text(
                                when (route) {
                                    "dashboard" -> "Ingresos"
                                    "budget" -> "Gastos"
                                    "savings" -> "Ahorros"
                                    "reminders" -> "Recordatorios"
                                    "settings" -> "Configuración"
                                    else -> route
                                },
                                fontSize = 8.sp // ajustar según necesites
                            )
                        }
                    )
                }
            }
        }
    ) { padding ->
        // Pantalla mostrada según el ítem seleccionado
        when (selectedItem) {
            "dashboard" -> DashboardScreen(viewModel)
            "budget" -> BudgetScreen(viewModel)
            "savings" -> SavingsScreen(viewModel)
            "reminders" -> RemindersScreen(viewModel = remindersVM)
            // (opcional) puedes añadir una futura pantalla para "settings"
        }

        // Si showGate está activo, muestra el pop-up con el último recordatorio
        if (showGate) {
            AlertDialog(
                onDismissRequest = { showGate = false },
                title = { Text("Recordatorio") },
                text = {
                    if (lastReminder != null) {
                        Text(lastReminder.text)
                    } else {
                        Text("No tienes recordatorios pendientes.")
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { showGate = false }) {
                        Text("✕ Cerrar")
                    }
                }
            )
        }
    }
}




