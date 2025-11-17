package com.example.practico4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practico4.data.models.Punto
import com.example.practico4.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(private val repo: Repository = Repository()) : ViewModel() {

    private val _puntos = MutableStateFlow<List<Punto>>(emptyList())
    val puntos: StateFlow<List<Punto>> = _puntos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun cargarPuntos(rutaId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _puntos.value = repo.obtenerPuntos(rutaId)
            _loading.value = false
        }
    }

    fun agregarPunto(
        rutaId: Int,
        lat: Double,
        lon: Double,
        onComplete: (Boolean) -> Unit = {}
    ) {
        viewModelScope.launch {
            _loading.value = true

            val creado = repo.crearPunto(
                Punto(
                    latitude = lat.toString(),
                    longitude = lon.toString(),
                    route_id = rutaId
                )
            )

            _loading.value = false

            if (creado != null) {
                cargarPuntos(rutaId)
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    fun eliminarPunto(
        puntoId: Int,
        rutaId: Int,
        onComplete: (Boolean) -> Unit = {}
    ) {
        viewModelScope.launch {
            _loading.value = true
            val exito = repo.eliminarPunto(puntoId)
            _loading.value = false

            if (exito) {
                cargarPuntos(rutaId)
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }
}
