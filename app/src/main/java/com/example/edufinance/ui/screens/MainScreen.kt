package com.example.edufinance.ui.screens

import android.graphics.drawable.PaintDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.edufinance.ui.viewmodel.DashboardViewModel
import com.example.edufinance.ui.viewmodel.RemindersViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.edufinance.ui.utils.NetworkMonitor
import kotlinx.coroutines.launch
import com.example.edufinance.R
import androidx.compose.ui.res.painterResource


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

    // Obtiene el último recordatorio disponible (si existe)
    val lastReminder = remindersVM.reminders.firstOrNull()

    val GreenPrimaryDark = Color(0xFF53B38A)
    val AccentYellow = Color(0xFFFFD166)
    val White = Color.White

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var showGate by rememberSaveable { mutableStateOf(showReminderOnStart) }
    var showNoInternet by rememberSaveable { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }

    val networkMonitor = remember { NetworkMonitor(context) }
    val isConnected by networkMonitor.isConnected.collectAsStateWithLifecycle()



// Cuando se pierda conexión
    LaunchedEffect(isConnected) {
        if (!isConnected) {
            dialogTitle = "Sin conexión a Internet"
            dialogMessage = "Parece que no tienes conexión. Algunas funciones pueden no estar disponibles."
            showNoInternet = true
        }
    }

// Mostramos el diálogo según el estado
    if (showNoInternet || showGate) {
        AlertDialog(
            onDismissRequest = { /* vacío para evitar cierre automático */ },
            title = {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    // Imagen decorativa del aviso
                    if (showNoInternet) {
                        Image(
                            painter = painterResource(id = R.drawable.no_internet),
                            contentDescription = "Sin conexión",
                            modifier = Modifier
                                .size(90.dp)
                                .padding(bottom = 8.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    Text(
                        text = if (showGate) "Recordatorio" else dialogTitle,
                        color = Color(0xFF007F5F),
                        fontSize = 20.sp
                    )
                }
            },
            text = {
                Text(
                    text = if (showGate)
                        (lastReminder?.text ?: "No tienes recordatorios pendientes.")
                    else dialogMessage,
                    color = Color(0xFF004D3D)
                )
            },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = {
                        showGate = false
                        showNoInternet = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF121212))
                ) {
                    Text("✕ Cerrar")
                }
            },
            containerColor = Color.White,
            tonalElevation = 6.dp
        )
    }


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
            modifier = Modifier.background(White),

            // 🔹 Aquí agregamos el snackbar host para mostrar el aviso de red
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        containerColor = Color(0xFFB00020), // fondo rojo o color de error
                        contentColor = Color.White
                    ) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.no_internet),
                                contentDescription = "Sin conexión",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(text = data.visuals.message)
                        }
                    }
                }
            },

            bottomBar = {
                NavigationBar(
                    containerColor = GreenPrimaryDark,
                    tonalElevation = 6.dp
                ) {
                    items.forEach { (route, icon) ->
                        val isSelected = selectedItem == route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedItem = route },
                            icon = {
                                Icon(
                                    icon,
                                    contentDescription = route,
                                    tint = if (isSelected) AccentYellow else White
                                )
                            },
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
                                    fontSize = 9.sp,
                                    color = if (isSelected) AccentYellow else White
                                )
                            },
                            alwaysShowLabel = true,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = AccentYellow,
                                unselectedIconColor = White,
                                selectedTextColor = AccentYellow,
                                unselectedTextColor = White,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        ) {
        padding ->
        when (selectedItem) {
            "dashboard" -> DashboardScreen(viewModel)
            "budget" -> BudgetScreen(viewModel)
            "savings" -> SavingsScreen(viewModel)
            "reminders" -> RemindersScreen(viewModel = remindersVM)
        }
    }

            if (showGate) {
                AlertDialog(
                    onDismissRequest = { showGate = false },
                    title = { Text("Recordatorio", color = GreenPrimaryDark, fontSize = 18.sp) },
                    text = {
                        Text(
                            lastReminder?.text ?: "No tienes recordatorios pendientes.",
                            color = GreenPrimaryDark
                        )
                    },
                    confirmButton = {},
                    dismissButton = {
                        TextButton(
                            onClick = { showGate = false },
                            colors = ButtonDefaults.textButtonColors(contentColor = AccentYellow)
                        ) {
                            Text("✕ Cerrar")
                        }
                    }
                )
            }
        }










