package com.example.practico4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practico4.data.models.Ruta
import com.example.practico4.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoutesViewModel(private val repo: Repository = Repository()) : ViewModel() {

    private val _rutas = MutableStateFlow<List<Ruta>>(emptyList())
    val rutas: StateFlow<List<Ruta>> = _rutas

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun cargarRutas(usuario: String) {
        if (usuario.isBlank()) return

        viewModelScope.launch {
            _loading.value = true
            _rutas.value = repo.obtenerRutas(usuario)
            _loading.value = false
        }
    }

    fun crearRuta(nombre: String, usuario: String, onComplete: (Boolean) -> Unit = {}) {
        if (nombre.isBlank() || usuario.isBlank()) {
            onComplete(false)
            return
        }

        viewModelScope.launch {
            _loading.value = true
            val nuevaRuta = repo.crearRuta(
                Ruta(
                    name = nombre,
                    username = usuario
                )
            )
            _loading.value = false

            if (nuevaRuta != null) {
                cargarRutas(usuario)
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    fun eliminarRuta(id: Int, usuario: String, onComplete: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            _loading.value = true
            val exito = repo.eliminarRuta(id)
            _loading.value = false

            if (exito) {
                cargarRutas(usuario)
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }
}
