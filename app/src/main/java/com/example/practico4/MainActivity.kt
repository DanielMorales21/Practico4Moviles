package com.example.practico4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.practico4.ui.navigation.AppNav
import com.example.practico4.ui.theme.Practico4Theme
import com.example.practico4.viewmodel.AuthViewModel
import com.example.practico4.viewmodel.MapViewModel
import com.example.practico4.viewmodel.RoutesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practico4Theme {

                val nav = rememberNavController()

                val authViewModel: AuthViewModel = viewModel()
                val routesViewModel: RoutesViewModel = viewModel()
                val mapViewModel: MapViewModel = viewModel()

                AppNav(
                    nav = nav,
                    authViewModel = authViewModel,
                    routesViewModel = routesViewModel,
                    mapViewModel = mapViewModel
                )

            }
        }
    }
}
