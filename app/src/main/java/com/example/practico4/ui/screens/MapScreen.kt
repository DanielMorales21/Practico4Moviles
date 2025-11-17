package com.example.practico4.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.example.practico4.viewmodel.MapViewModel


@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    rutaId: Int,
    onVolver: () -> Unit
) {
    val puntos by mapViewModel.puntos.collectAsState()
    val context = LocalContext.current
    var seleccionado by remember { mutableStateOf<LatLng?>(null) }

    val camState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-17.7833, -63.1821), 13f)
    }

    LaunchedEffect(rutaId) {
        mapViewModel.cargarPuntos(rutaId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = camState,
            onMapClick = { latLng -> seleccionado = latLng },
            onMapLongClick = { latLng ->
                mapViewModel.agregarPunto(rutaId, latLng.latitude, latLng.longitude) { exito ->
                    Toast.makeText(context, if (exito) "Punto agregado" else "Error al agregar", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            puntos.forEach { p ->
                val pos = LatLng(p.latitude.toDouble(), p.longitude.toDouble())
                Marker(
                    state = MarkerState(position = pos),
                    title = "Punto ${p.id ?: "—"}",
                    snippet = "Tocar info para eliminar",
                    onInfoWindowClick = {
                        p.id?.let { puntoId ->
                            mapViewModel.eliminarPunto(puntoId, rutaId) { exito ->
                                Toast.makeText(context, if (exito) "Punto eliminado" else "No se pudo eliminar", Toast.LENGTH_SHORT).show()
                            }
                        } ?: run {
                            Toast.makeText(context, "Punto sin id, no se puede eliminar", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }


            seleccionado?.let { LatLng ->
                Marker(state = MarkerState(position = LatLng), title = "Nuevo (confirmar con +)")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            FloatingActionButton(onClick = {
                val sel = seleccionado
                if (sel != null) {
                    mapViewModel.agregarPunto(rutaId, sel.latitude, sel.longitude) { exito ->
                        Toast.makeText(context, if (exito) "Punto guardado" else "Error al guardar", Toast.LENGTH_SHORT).show()
                        if (exito) seleccionado = null
                    }
                } else {
                    Toast.makeText(context, "Tocá el mapa para seleccionar un punto", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("+")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onVolver) { Text("Volver") }
        }
    }
}
