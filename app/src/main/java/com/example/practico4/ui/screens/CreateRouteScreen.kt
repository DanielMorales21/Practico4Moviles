package com.example.practico4.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practico4.viewmodel.AuthViewModel
import com.example.practico4.viewmodel.RoutesViewModel


@Composable
fun CreateRouteScreen(
    authViewModel: AuthViewModel,
    routesViewModel: RoutesViewModel,
    onVolver: () -> Unit
) {
    val usuario by authViewModel.usuario.collectAsState()
    var nombre by remember { mutableStateOf("") }
    val loading by routesViewModel.loading.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Crear Ruta para $usuario", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            if (nombre.isNotBlank()) {
                routesViewModel.crearRuta(nombre.trim(), usuario) { exito ->
                    if (exito) {
                        nombre = ""
                        onVolver()
                    }
                }
            }
        }) {
            Text("Guardar")
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (loading) Text("Guardando...")
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onVolver) { Text("Volver") }
    }
}
