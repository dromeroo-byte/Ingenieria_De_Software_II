package com.example.pruebatecnicainter.ui.datos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.pruebatecnicainter.data.local.AppDatabase
import com.example.pruebatecnicainter.data.local.TablaEsquemaEntity
import com.example.pruebatecnicainter.data.repository.DatosRepository
import kotlinx.coroutines.launch

/**
 * ViewModel de la Capa de Datos.
 * Orquesta la sincronización del esquema de tablas y expone
 * la lista de tablas a la pantalla.
 */
class DatosViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DatosRepository

    // Estado: mensaje informativo del proceso de sincronización.
    private val _mensaje = mutableStateOf("Cargando tablas...")
    val mensaje: State<String> = _mensaje

    // Estado: lista de tablas para mostrar en pantalla.
    private val _tablas = mutableStateOf<List<TablaEsquemaEntity>>(emptyList())
    val tablas: State<List<TablaEsquemaEntity>> = _tablas

    init {
        val dao = AppDatabase.obtenerInstancia(application).tablaEsquemaDao()
        repository = DatosRepository(dao)
    }

    /**
     * Sincroniza el esquema desde la API y luego carga las tablas guardadas.
     */
    fun cargarTablas() {
        viewModelScope.launch {
            // Primero intentamos sincronizar desde la API.
            val cantidad = repository.sincronizarEsquema()

            // Luego leemos lo que quedó guardado en la BD local.
            val tablasGuardadas = repository.obtenerTablasGuardadas()
            _tablas.value = tablasGuardadas

            _mensaje.value = when {
                cantidad == null && tablasGuardadas.isEmpty() ->
                    "No se pudieron obtener las tablas del servidor."
                cantidad == null ->
                    "Mostrando ${tablasGuardadas.size} tablas guardadas (sin conexión al servidor)."
                else ->
                    "Se sincronizaron ${tablasGuardadas.size} tablas correctamente."
            }
        }
    }
}