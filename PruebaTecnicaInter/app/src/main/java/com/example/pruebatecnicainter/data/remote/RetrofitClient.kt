package com.example.pruebatecnicainter.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Cliente único de Retrofit para toda la aplicación.
 * Centraliza la configuración de red: URL base, tiempos de espera y logs.
 */
object RetrofitClient {

    // Dirección base del servidor de la empresa.
    private const val BASE_URL = "https://apitesting.interrapidisimo.co/"

    // Interceptor que imprime en Logcat lo que se envía y recibe (útil para depurar).
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Cliente HTTP con tiempos de espera y el log activado.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Instancia de la interfaz ApiService lista para usarse.
     * Se crea una sola vez y se reutiliza (patrón singleton).
     */
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}