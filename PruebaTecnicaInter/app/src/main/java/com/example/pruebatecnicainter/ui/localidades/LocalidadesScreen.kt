package com.example.pruebatecnicainter.ui.localidades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Pantalla LOCALIDADES.
 * Muestra la abreviación de ciudad y el nombre completo de cada
 * localidad obtenida del endpoint.
 */
@Composable
fun LocalidadesScreen(
    modifier: Modifier = Modifier,
    viewModel: LocalidadesViewModel = viewModel()
) {
    val mensaje by viewModel.mensaje
    val localidades by viewModel.localidades

    // Al abrir la pantalla, cargamos las localidades.
    LaunchedEffect(Unit) {
        viewModel.cargarLocalidades()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Localidades de Recogida",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(localidades) { localidad ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = localidad.AbreviacionCiudad ?: "Sin abreviación",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = localidad.NombreCompleto ?: "Sin nombre"
                        )
                    }
                }
            }
        }
    }
}