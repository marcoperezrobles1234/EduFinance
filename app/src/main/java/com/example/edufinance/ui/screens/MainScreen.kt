package com.example.edufinance.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.edufinance.ui.viewmodel.DashboardViewModel

@Composable
fun MainScreen(navController: NavHostController, viewModel: DashboardViewModel) {
    // Variable que guarda qué elemento está seleccionado actualmente en la barra inferior
    var selectedItem by remember { mutableStateOf("dashboard") }

    // Lista de elementos que aparecerán en la barra de navegación inferior
    // Cada uno tiene una ruta (string) y un ícono asociado
    val items = listOf(
        "dashboard" to Icons.Default.MonetizationOn,
        "budget" to Icons.Default.AccountBalanceWallet,
        "savings" to Icons.Default.Savings,
        "reminders" to Icons.Default.Notifications,
        "settings" to Icons.Default.Settings
    )

    // Estructura principal del diseño con barra inferior
    Scaffold(
        bottomBar = {
            // Componente de Material3 que representa la barra de navegación inferior
            NavigationBar {
                // Se recorren los elementos definidos en 'items'
                items.forEach { (route, icon) ->
                    // Cada ítem de la barra
                    NavigationBarItem(
                        // Se marca como seleccionado si coincide con la ruta activa
                        selected = selectedItem == route,

                        // Al hacer clic se actualiza el ítem seleccionado
                        onClick = { selectedItem = route },

                        // Ícono visual del botón
                        icon = { Icon(icon, contentDescription = route) },

                        // Etiqueta que se muestra debajo del ícono
                        label = {
                            Text(
                                when(route) {
                                    "dashboard" -> "Ingresos"          // Pantalla principal
                                    "budget" -> "Gastos"               // Presupuesto o gastos
                                    "savings" -> "Ahorros"             // Ahorros semanales o mensuales
                                    "reminders" -> "Recordatorios"     // Alertas o avisos financieros
                                    "settings" -> "Configuración"      // Ajustes generales de la app
                                    else -> route
                                }
                            )
                        }
                    )
                }
            }
        }
    ) { padding ->
        // Contenido principal que se muestra según el ítem seleccionado
        when (selectedItem) {
            "dashboard" -> DashboardScreen(viewModel)   // Muestra la pantalla de ingresos
            "budget" -> BudgetScreen(viewModel)         // Muestra la pantalla de gastos
        }
    }
}





