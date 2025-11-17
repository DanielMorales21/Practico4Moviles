package com.example.practico4.data.api

import com.example.practico4.data.models.Punto
import com.example.practico4.data.models.Ruta
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // === RUTAS ===
    @GET("routes/{username}")
    suspend fun getRutasUsuario(@Path("username") username: String): Response<List<Ruta>>

    @POST("routes")
    suspend fun crearRuta(@Body ruta: Ruta): Response<Ruta>

    @PUT("routes/{id}")
    suspend fun actualizarRuta(@Path("id") id: Int, @Body ruta: Ruta): Response<Ruta>

    @DELETE("routes/{id}")
    suspend fun eliminarRuta(@Path("id") id: Int): Response<Unit>

    // === PUNTOS ===
    @GET("routes/{routeId}/locations")
    suspend fun getPuntosRuta(@Path("routeId") routeId: Int): Response<List<Punto>>

    @POST("locations")
    suspend fun crearPunto(@Body punto: Punto): Response<Punto>

    @PUT("locations/{id}")
    suspend fun actualizarPunto(@Path("id") id: Int, @Body punto: Punto): Response<Punto>

    @DELETE("locations/{id}")
    suspend fun eliminarPunto(@Path("id") id: Int): Response<Unit>
}
