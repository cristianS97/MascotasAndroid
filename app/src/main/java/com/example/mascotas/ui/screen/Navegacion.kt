package com.example.mascotas.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mascotas.ui.screen.petsList.PetsListScreen
import com.example.mascotas.ui.screen.petsList.PetsViewModel

@Composable
fun NavegacionApp(petsViewModel: PetsViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenPetsList) {
        composable<ScreenPetsList> {
            PetsListScreen(petsViewModel = petsViewModel)
        }
    }
}