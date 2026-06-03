package com.example.pruebatecnicainter.data.repository

import com.example.pruebatecnicainter.data.local.UsuarioDao
import com.example.pruebatecnicainter.data.local.UsuarioEntity
import com.example.pruebatecnicainter.data.remote.LoginRequest
import com.example.pruebatecnicainter.data.remote.RetrofitClient
import com.example.pruebatecnicainter.ui.seguridad.LoginResultado

/**
 * Repositorio de la Capa de Seguridad.
 * Responsabilidad única: gestionar las operaciones de seguridad
 * (control de versiones y login), incluyendo el guardado local del usuario.
 *
 * @param usuarioDao acceso a la tabla 'usuario' de la base de datos local.
 */
class SeguridadRepository(
    private val usuarioDao: UsuarioDao
) {

    /**
     * Consulta la versión actual del aplicativo en la API.
     * @return el número de versión como entero, o null si falla.
     */
    suspend fun obtenerVersionApi(): Int? {
        return try {
            val response = RetrofitClient.apiService.consultarVersion()
            if (response.isSuccessful) {
                val texto = response.body()?.string()
                    ?.replace("\"", "")
                    ?.trim()
                texto?.toIntOrNull()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Autentica al usuario contra la API y, si es exitoso,
     * guarda sus datos en la base de datos local.
     * @return LoginResultado.Exito con los datos, o LoginResultado.Error.
     */
    suspend fun autenticar(): LoginResultado {
        return try {
            val response = RetrofitClient.apiService.autenticarUsuario(
                usuario = "pam.meredy21",
                identificacion = "987204545",
                idUsuario = "pam.meredy21",
                idCentroServicio = "1295",
                nombreCentroServicio = "PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45",
                body = LoginRequest()
            )

            if (response.isSuccessful) {
                val cuerpo = response.body()
                val usuario = cuerpo?.Usuario ?: "Sin dato"
                val identificacion = cuerpo?.Identificacion ?: "Sin dato"
                val nombre = cuerpo?.Nombre ?: "Sin dato"

                // Guardamos el usuario autenticado en la base de datos local.
                usuarioDao.guardarUsuario(
                    UsuarioEntity(
                        usuario = usuario,
                        identificacion = identificacion,
                        nombre = nombre
                    )
                )

                LoginResultado.Exito(usuario, identificacion, nombre)
            } else {
                LoginResultado.Error(
                    "Error de autenticación. Código: ${response.code()}"
                )
            }
        } catch (e: Exception) {
            LoginResultado.Error(
                "No se pudo conectar con el servidor: ${e.message}"
            )
        }
    }

    /**
     * Lee el usuario almacenado en la base de datos local.
     * @return el usuario guardado, o null si no hay ninguno.
     */
    suspend fun obtenerUsuarioGuardado(): UsuarioEntity? {
        return usuarioDao.obtenerUsuario()
    }
}