package com.example.practico4.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.practico4.ui.screens.*
import com.example.practico4.viewmodel.AuthViewModel
import com.example.practico4.viewmodel.MapViewModel
import com.example.practico4.viewmodel.RoutesViewModel

@Composable
fun AppNav(
    nav: NavHostController,
    authViewModel: AuthViewModel,
    routesViewModel: RoutesViewModel,
    mapViewModel: MapViewModel
) {
    NavHost(navController = nav, startDestination = "splash") {


        composable("splash") {
            SplashScreen { usuario ->
                authViewModel.setUsuario(usuario)
                routesViewModel.cargarRutas(usuario)
                nav.navigate("routes")
            }
        }


        composable("routes") {
            RoutesListScreen(
                authViewModel = authViewModel,
                routesViewModel = routesViewModel,
                onCrearRuta = {
                    nav.navigate("createRoute")
                },
                onIrMapa = { rutaId ->
                    mapViewModel.cargarPuntos(rutaId)
                    nav.navigate("map/$rutaId")
                }
            )
        }


        composable("createRoute") {
            CreateRouteScreen(
                authViewModel = authViewModel,
                routesViewModel = routesViewModel,
                onVolver = { nav.popBackStack() }
            )
        }


        composable("map/{rutaId}") { backStack ->
            val rutaId = backStack.arguments?.getString("rutaId")?.toIntOrNull() ?: 0

            MapScreen(
                mapViewModel = mapViewModel,
                rutaId = rutaId,
                onVolver = { nav.popBackStack() }
            )
        }
    }
}
