package com.example.pruebatecnicainter.ui.seguridad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Pantalla de inicio (SPLASH).
 * Ejecuta el control de versiones y el login al arrancar.
 * Cuando el proceso termina, invoca onArranqueListo con los datos del usuario.
 *
 * @param onArranqueListo callback que recibe (usuario, identificacion, nombre)
 *                        cuando el arranque finaliza correctamente.
 */
@Composable
fun SplashScreen(
    onArranqueListo: (String, String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SeguridadViewModel = viewModel()
) {
    val mensajeVersion by viewModel.mensajeVersion
    val estadoLogin by viewModel.estadoLogin
    val usuario by viewModel.usuarioActual
    val arranqueCompletado by viewModel.arranqueCompletado

    // Dispara el arranque una sola vez al mostrar la pantalla.
    LaunchedEffect(Unit) {
        viewModel.iniciarArranque()
    }

    // Cuando el arranque termina, navegamos a Home con los datos reales.
    LaunchedEffect(arranqueCompletado) {
        if (arranqueCompletado) {
            val datos = usuario
            if (datos != null) {
                onArranqueListo(datos.usuario, datos.identificacion, datos.nombre)
            } else {
                // Si el login falló, igual pasamos con datos por defecto.
                onArranqueListo("Sin dato", "Sin dato", "Sin dato")
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "InterRapidísimo",
            style = MaterialTheme.typography.headlineMedium
        )

        CircularProgressIndicator(
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Text(
            text = mensajeVersion,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = estadoLogin,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}