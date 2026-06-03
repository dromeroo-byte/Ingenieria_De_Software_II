package com.example.pruebatecnicainter.ui.seguridad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.pruebatecnicainter.data.local.AppDatabase
import com.example.pruebatecnicainter.data.repository.SeguridadRepository
import kotlinx.coroutines.launch

/**
 * ViewModel de la Capa de Seguridad.
 * Gestiona el control de versiones y el login durante el arranque (Splash),
 * exponiendo el estado a la pantalla mediante propiedades observables.
 */
class SeguridadViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SeguridadRepository

    // Versión local de la aplicación (definida manualmente para la prueba).
    private val versionLocal = 95

    // Estado: mensaje del control de versiones.
    private val _mensajeVersion = mutableStateOf("Verificando versión...")
    val mensajeVersion: State<String> = _mensajeVersion

    // Estado: mensaje del proceso de login.
    private val _estadoLogin = mutableStateOf("Iniciando sesión...")
    val estadoLogin: State<String> = _estadoLogin

    // Estado: datos del usuario autenticado.
    private val _usuarioActual = mutableStateOf<LoginResultado.Exito?>(null)
    val usuarioActual: State<LoginResultado.Exito?> = _usuarioActual

    // Estado: indica si el proceso de arranque (versión + login) ya terminó.
    private val _arranqueCompletado = mutableStateOf(false)
    val arranqueCompletado: State<Boolean> = _arranqueCompletado

    init {
        val dao = AppDatabase.obtenerInstancia(application).usuarioDao()
        repository = SeguridadRepository(dao)
    }

    /**
     * Ejecuta el arranque completo: primero verifica la versión,
     * luego realiza el login. Al terminar, marca arranqueCompletado.
     */
    fun iniciarArranque() {
        viewModelScope.launch {
            // 1. Control de versiones.
            val versionApi = repository.obtenerVersionApi()
            _mensajeVersion.value = when {
                versionApi == null ->
                    "No se pudo obtener la versión del servidor."
                versionLocal < versionApi ->
                    "Versión desactualizada (local $versionLocal < servidor $versionApi)."
                versionLocal > versionApi ->
                    "Versión local ($versionLocal) superior al servidor ($versionApi)."
                else ->
                    "Aplicación actualizada (versión $versionApi)."
            }

            // 2. Login.
            when (val resultado = repository.autenticar()) {
                is LoginResultado.Exito -> {
                    _usuarioActual.value = resultado
                    _estadoLogin.value = "Sesión iniciada correctamente."
                }
                is LoginResultado.Error -> {
                    _estadoLogin.value = resultado.mensaje
                }
            }

            // 3. Pausa breve para que se alcance a ver el mensaje de versión.
            kotlinx.coroutines.delay(3000)

            // 4. Marcamos que el arranque terminó (haya o no login exitoso).
            _arranqueCompletado.value = true
        }
    }
}