package com.example.practico4.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val _usuario = MutableStateFlow("")
    val usuario: StateFlow<String> = _usuario

    fun setUsuario(nombre: String) {
        _usuario.value = nombre.trim()
    }
}
