package com.example.practico4.data.repository

import com.example.practico4.data.api.RetrofitClient
import com.example.practico4.data.models.Punto
import com.example.practico4.data.models.Ruta

class Repository {

    private val api = RetrofitClient.instance


    suspend fun obtenerRutas(username: String): List<Ruta> {
        return try {
            val response = api.getRutasUsuario(username)
            if (response.isSuccessful) response.body() ?: emptyList()
            else emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun crearRuta(ruta: Ruta): Ruta? {
        return try {
            val response = api.crearRuta(ruta)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun actualizarRuta(id: Int, ruta: Ruta): Ruta? {
        return try {
            val response = api.actualizarRuta(id, ruta)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun eliminarRuta(id: Int): Boolean {
        return try {
            val response = api.eliminarRuta(id)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun obtenerPuntos(rutaId: Int): List<Punto> {
        return try {
            val response = api.getPuntosRuta(rutaId)
            if (response.isSuccessful) response.body() ?: emptyList()
            else emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun crearPunto(punto: Punto): Punto? {
        return try {
            val response = api.crearPunto(punto)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun actualizarPunto(id: Int, punto: Punto): Punto? {
        return try {
            val response = api.actualizarPunto(id, punto)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun eliminarPunto(id: Int): Boolean {
        return try {
            val response = api.eliminarPunto(id)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
