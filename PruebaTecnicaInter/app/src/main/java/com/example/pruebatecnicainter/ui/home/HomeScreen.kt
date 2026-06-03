package com.example.pruebatecnicainter.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Pantalla principal (HOME).
 * Muestra los datos del usuario autenticado y permite navegar
 * a las pantallas de Tablas y Localidades.
 *
 * @param usuario datos del usuario logueado.
 * @param identificacion identificación del usuario.
 * @param nombre nombre del usuario.
 * @param onIrATablas acción al pulsar el botón "Tablas".
 * @param onIrALocalidades acción al pulsar el botón "Localidades".
 */
@Composable
fun HomeScreen(
    usuario: String,
    identificacion: String,
    nombre: String,
    onIrATablas: () -> Unit,
    onIrALocalidades: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido",
            style = MaterialTheme.typography.headlineMedium
        )

        // Tarjeta con los datos del usuario.
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Usuario: $usuario")
                Text(text = "Identificación: $identificacion")
                Text(text = "Nombre: $nombre")
            }
        }

        // Botones de navegación.
        Button(
            onClick = onIrATablas,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tablas")
        }

        Button(
            onClick = onIrALocalidades,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Localidades")
        }
    }
}