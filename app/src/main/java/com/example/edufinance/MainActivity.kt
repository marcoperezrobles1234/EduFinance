package com.example.edufinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.edufinance.ui.navigation.AppNavGraph
import com.example.edufinance.ui.theme.EduFinanceTheme

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite que la app se dibuje bajo la barra de estado y navegación (modo edge-to-edge)
        enableEdgeToEdge()

        // Configuración del contenido de la actividad usando Jetpack Compose
        setContent {
            // Se aplica el tema personalizado de la app
            EduFinanceTheme {

                // Controlador de navegación que gestiona las rutas de la app
                val navController = rememberNavController()

                // Scaffold es la estructura principal que permite barras, FAB, etc.
                Scaffold(
                    modifier = Modifier.fillMaxSize() // Ocupa toda la pantalla
                ) { innerPadding ->  // innerPadding asegura que el contenido no quede debajo de la barra inferior u otros elementos

                    // Grafo de navegación principal de la app
                    AppNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding) // Aplica el padding del Scaffold al contenido
                    )
                }
            }
        }
    }
}











