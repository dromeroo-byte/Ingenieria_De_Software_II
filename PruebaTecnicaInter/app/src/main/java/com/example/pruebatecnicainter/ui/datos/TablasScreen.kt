package com.example.pruebatecnicainter.ui.datos

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
 * Pantalla TABLAS.
 * Muestra la lista de tablas obtenidas del esquema y guardadas localmente.
 */
@Composable
fun TablasScreen(
    modifier: Modifier = Modifier,
    viewModel: DatosViewModel = viewModel()
) {
    val mensaje by viewModel.mensaje
    val tablas by viewModel.tablas

    // Al abrir la pantalla, cargamos las tablas.
    LaunchedEffect(Unit) {
        viewModel.cargarTablas()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Tablas del Esquema",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista eficiente: solo dibuja los elementos visibles.
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tablas) { tabla ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = tabla.nombreTabla,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = "Clave primaria: ${tabla.pk}")
                        Text(text = "Número de campos: ${tabla.numeroCampos}")
                        Text(
                            text = "Actualización: ${tabla.fechaActualizacion}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}