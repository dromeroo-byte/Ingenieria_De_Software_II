package com.example.pruebatecnicainter.ui.localidades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.pruebatecnicainter.data.remote.LocalidadResponse
import com.example.pruebatecnicainter.data.repository.LocalidadesRepository
import kotlinx.coroutines.launch

/**
 * ViewModel de Localidades.
 * Orquesta la obtención de localidades y expone la lista a la pantalla.
 */
class LocalidadesViewModel : ViewModel() {

    private val repository = LocalidadesRepository()

    // Estado: mensaje informativo.
    private val _mensaje = mutableStateOf("Cargando localidades...")
    val mensaje: State<String> = _mensaje

    // Estado: lista de localidades para mostrar.
    private val _localidades = mutableStateOf<List<LocalidadResponse>>(emptyList())
    val localidades: State<List<LocalidadResponse>> = _localidades

    /**
     * Obtiene las localidades desde la API y actualiza el estado.
     */
    fun cargarLocalidades() {
        viewModelScope.launch {
            val lista = repository.obtenerLocalidades()

            if (lista != null) {
                _localidades.value = lista
                _mensaje.value = "Se obtuvieron ${lista.size} localidades."
            } else {
                _mensaje.value = "No se pudieron obtener las localidades."
            }
        }
    }
}