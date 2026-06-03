package com.example.pruebatecnicainter.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @GET("apicontrollerpruebas/api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl")
    suspend fun consultarVersion(): Response<okhttp3.ResponseBody>

    @Headers(
        "Accept: text/json",
        "IdAplicativoOrigen: 9",
        "Content-Type: application/json"
    )
    @POST("FtEntregaElectronica/MultiCanales/ApiSeguridadPruebas/api/Seguridad/AuthenticaUsuarioApp")
    suspend fun autenticarUsuario(
        @Header("Usuario") usuario: String,
        @Header("Identificacion") identificacion: String,
        @Header("IdUsuario") idUsuario: String,
        @Header("IdCentroServicio") idCentroServicio: String,
        @Header("NombreCentroServicio") nombreCentroServicio: String,
        @Body body: LoginRequest
    ): Response<LoginResponse>

    /**
     * Obtiene el esquema de tablas del sistema (Capa de Datos).
     * Requiere los mismos headers de identificación que el login.
     */
    @Headers(
        "Accept: text/json",
        "IdAplicativoOrigen: 9",
        "Content-Type: application/json"
    )
    @GET("apicontrollerpruebas/api/SincronizadorDatos/ObtenerEsquema/true")
    suspend fun obtenerEsquema(
        @Header("Usuario") usuario: String,
        @Header("Identificacion") identificacion: String,
        @Header("IdUsuario") idUsuario: String,
        @Header("IdCentroServicio") idCentroServicio: String,
        @Header("NombreCentroServicio") nombreCentroServicio: String
    ): Response<List<TablaEsquemaResponse>>

    /**
     * Obtiene las localidades de recogida (Capa de Presentación).
     * Este endpoint no requiere headers de autenticación.
     */
    @GET("apicontrollerpruebas/api/ParametrosFramework/ObtenerLocalidadesRecogidas")
    suspend fun obtenerLocalidades(): Response<List<LocalidadResponse>>
}