package com.example.edufinance.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.edufinance.ui.screens.*
import com.example.edufinance.ui.viewmodel.DashboardViewModel
import com.example.edufinance.ui.viewmodel.RemindersViewModel

// Clase sellada que define las rutas (pantallas) disponibles dentro de la aplicación
sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")      // Pantalla de bienvenida
    object Login : Screen("login")          // Pantalla de inicio de sesión
    object Register : Screen("register")    // Pantalla de registro de usuario
    object Dashboard : Screen("dashboard")  // Pantalla principal (panel de control)
    object Budget : Screen("budget")        // Pantalla de presupuesto
    object Reminders : Screen("reminders")  // Pantalla de recordatorios
}

@Composable
fun AppNavGraph(
    navController: NavHostController, // Controlador de navegación entre pantallas
    modifier: Modifier = Modifier     // Permite aplicar modificaciones de diseño
) {
    // Componente que define el gráfico de navegación de la app
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = modifier
    ) {
        // Pantalla de bienvenida
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }

        // Pantalla de inicio de sesión
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    // Al iniciar sesión, se navega al Dashboard con el parámetro showReminder=true
                    navController.navigate(Screen.Dashboard.route + "?showReminder=true") {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Pantalla de registro de usuario
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Pantalla principal (Dashboard) con argumento opcional showReminder
        composable(
            route = Screen.Dashboard.route + "?showReminder={showReminder}",
            arguments = listOf(
                navArgument("showReminder") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val showReminder = backStackEntry.arguments?.getBoolean("showReminder") == true
            val dashboardViewModel: DashboardViewModel = viewModel()
            MainScreen(
                navController = navController,
                viewModel = dashboardViewModel,
                showReminderOnStart = showReminder // Activa el pop-up al iniciar sesión
            )
        }

        // Pantalla de presupuesto semanal
        composable(Screen.Budget.route) {
            val dashboardViewModel: DashboardViewModel = viewModel()
            BudgetScreen(viewModel = dashboardViewModel)
        }

        // Pantalla de recordatorios
        composable(Screen.Reminders.route) {
            val remindersViewModel: RemindersViewModel = viewModel()
            RemindersScreen(viewModel = remindersViewModel)
        }
    }
}

















