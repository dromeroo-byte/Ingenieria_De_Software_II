package com.example.pruebatecnicainter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pruebatecnicainter.ui.datos.TablasScreen
import com.example.pruebatecnicainter.ui.home.HomeScreen
import com.example.pruebatecnicainter.ui.localidades.LocalidadesScreen
import com.example.pruebatecnicainter.ui.seguridad.SplashScreen

/**
 * Define la navegación de la aplicación.
 * Arranca en Splash (versión + login) y, al completarse,
 * pasa a Home con los datos del usuario autenticado.
 */
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    // Guardamos los datos del usuario para pasarlos a Home tras el login.
    var usuario by remember { mutableStateOf("") }
    var identificacion by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        // Pantalla SPLASH (ruta inicial): versión + login.
        composable("splash") {
            SplashScreen(
                onArranqueListo = { u, i, n ->
                    // Guardamos los datos y navegamos a Home.
                    usuario = u
                    identificacion = i
                    nombre = n
                    navController.navigate("home") {
                        // Quitamos splash del historial para que "atrás" no regrese a ella.
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        // Pantalla HOME.
        composable("home") {
            HomeScreen(
                usuario = usuario,
                identificacion = identificacion,
                nombre = nombre,
                onIrATablas = { navController.navigate("tablas") },
                onIrALocalidades = { navController.navigate("localidades") }
            )
        }

        // Pantalla TABLAS.
        composable("tablas") {
            TablasScreen()
        }

        // Pantalla LOCALIDADES.
        composable("localidades") {
            LocalidadesScreen()
        }
    }
}