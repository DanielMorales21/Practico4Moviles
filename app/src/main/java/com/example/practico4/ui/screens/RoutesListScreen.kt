package com.example.practico4.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practico4.viewmodel.AuthViewModel
import com.example.practico4.viewmodel.RoutesViewModel

@Composable
fun RoutesListScreen(
    authViewModel: AuthViewModel,
    routesViewModel: RoutesViewModel,
    onCrearRuta: () -> Unit,
    onIrMapa: (Int) -> Unit
) {
    val usuario by authViewModel.usuario.collectAsState()
    val rutas by routesViewModel.rutas.collectAsState()
    val loading by routesViewModel.loading.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Rutas de: $usuario", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onCrearRuta) {
            Text("Crear Ruta")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (loading) {
            Text("Cargando...")
        } else {
            LazyColumn {
                items(rutas) { ruta ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .let { m ->
                                        if (ruta.id != null) m.clickable { onIrMapa(ruta.id) } else m
                                    }
                            ) {
                                Text(text = ruta.name, style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = "ID: ${ruta.id ?: "—"}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Column {
                                if (ruta.id != null) {
                                    Button(onClick = {
                                        routesViewModel.eliminarRuta(
                                            ruta.id,
                                            usuario
                                        ) { }
                                    }) {
                                        Text("Borrar")
                                    }
                                } else {
                                    Button(onClick = { /* no hace nada */ }, enabled = false) {
                                        Text("Borrar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
