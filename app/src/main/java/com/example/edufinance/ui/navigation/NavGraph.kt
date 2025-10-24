package com.example.edufinance.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.edufinance.ui.screens.*
import com.example.edufinance.ui.viewmodel.DashboardViewModel

// Clase sellada que define las rutas (pantallas) disponibles dentro de la aplicación
sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")     // Pantalla de bienvenida
    object Login : Screen("login")         // Pantalla de inicio de sesión
    object Register : Screen("register")   // Pantalla de registro de usuario
    object Dashboard : Screen("dashboard") // Pantalla principal (panel de control)
    object Budget : Screen("budget")       // Pantalla de presupuesto
}

@Composable
fun AppNavGraph(
    navController: NavHostController, // Controlador de navegación entre pantallas
    modifier: Modifier = Modifier     // Permite aplicar modificaciones de diseño
) {
    // Componente que define el gráfico de navegación de la app
    NavHost(
        navController = navController,              // Controlador que gestiona la navegación
        startDestination = Screen.Welcome.route,    // Pantalla inicial al abrir la app
        modifier = modifier
    ) {
        // Pantalla de bienvenida
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onLoginClick = { navController.navigate(Screen.Login.route) },          // Navega al login
                onRegisterClick = { navController.navigate(Screen.Register.route) }     // Navega al registro
            )
        }

        // Pantalla de inicio de sesión
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    // Si el login es exitoso, navega al Dashboard y elimina la pantalla de login del historial
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }, // Ir al registro
                onBackClick = { navController.popBackStack() }                            // Regresar a la anterior
            )
        }

        // Pantalla de registro de usuario
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    // Después de registrarse, vuelve al login eliminando el registro del historial
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    // Permite ir directamente al login
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() } // Regresar a la pantalla anterior
            )
        }

        // Pantalla principal (Dashboard)
        composable(Screen.Dashboard.route) {
            val viewModel: DashboardViewModel = viewModel() // Se obtiene el ViewModel para manejar datos del panel
            MainScreen(navController, viewModel)             // Carga la pantalla principal con su ViewModel
        }
    }
}
















