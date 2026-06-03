package com.example.pruebatecnicainter.data.repository

import com.example.pruebatecnicainter.data.local.TablaEsquemaDao
import com.example.pruebatecnicainter.data.local.TablaEsquemaEntity
import com.example.pruebatecnicainter.data.remote.RetrofitClient

/**
 * Repositorio de la Capa de Datos.
 * Responsabilidad única: obtener el esquema de tablas desde la API
 * y gestionarlo en la base de datos local.
 *
 * @param tablaEsquemaDao acceso a la tabla 'tabla_esquema' local.
 */
class DatosRepository(
    private val tablaEsquemaDao: TablaEsquemaDao
) {

    /**
     * Consume el endpoint de esquema, guarda las tablas en la BD local
     * y devuelve la cantidad de tablas guardadas.
     * @return número de tablas guardadas, o null si hubo error.
     */
    suspend fun sincronizarEsquema(): Int? {
        return try {
            val response = RetrofitClient.apiService.obtenerEsquema(
                usuario = "pam.meredy21",
                identificacion = "987204545",
                idUsuario = "pam.meredy21",
                idCentroServicio = "1295",
                nombreCentroServicio = "PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45"
            )

            if (response.isSuccessful) {
                val listaApi = response.body() ?: emptyList()

                // Convertimos cada elemento de la API en una entidad local.
                val entidades = listaApi.map { tabla ->
                    TablaEsquemaEntity(
                        nombreTabla = tabla.NombreTabla ?: "Sin nombre",
                        pk = tabla.Pk ?: "Sin PK",
                        numeroCampos = tabla.NumeroCampos ?: 0,
                        fechaActualizacion = tabla.FechaActualizacionSincro ?: "Sin fecha"
                    )
                }

                // Limpiamos lo anterior y guardamos lo nuevo (evita duplicados).
                tablaEsquemaDao.limpiarTablas()
                tablaEsquemaDao.guardarTablas(entidades)

                entidades.size
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Lee las tablas del esquema almacenadas en la BD local.
     */
    suspend fun obtenerTablasGuardadas(): List<TablaEsquemaEntity> {
        return tablaEsquemaDao.obtenerTablas()
    }
}