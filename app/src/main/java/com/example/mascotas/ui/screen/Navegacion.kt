package com.example.mascotas.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mascotas.ui.screen.petDetail.PetDetailScreen
import com.example.mascotas.ui.screen.petDetail.PetDetailViewModel
import com.example.mascotas.ui.screen.petsList.PetsListScreen
import com.example.mascotas.ui.screen.petsList.PetsViewModel

@Composable
fun NavegacionApp(petsViewModel: PetsViewModel, petDetailViewModel : PetDetailViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenPetsList) {
        composable<ScreenPetsList> {
            PetsListScreen(
                petsViewModel = petsViewModel,
                navigate = { idMascota ->
                    navController.navigate(ScreenPetDetailSerialize(idMascota = idMascota))
                }
            )
        }

        composable<ScreenPetDetailSerialize> { navBackStackEntry ->
            val data = navBackStackEntry.toRoute<ScreenPetDetailSerialize>()
            PetDetailScreen(
                idMascota = data.idMascota,
                petDetailViewModel = petDetailViewModel
            )
        }
    }
}