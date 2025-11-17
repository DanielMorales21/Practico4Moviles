package com.example.practico4.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color



@Composable
fun SplashScreen(onIngresar: (String) -> Unit) {
    var usuario by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(320.dp)) {
            Text(text = "Ingrese su usuario")
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { if (usuario.isNotBlank()) onIngresar(usuario.trim()) }) {
                Text(text = "Ingresar")
            }
        }
    }
}
