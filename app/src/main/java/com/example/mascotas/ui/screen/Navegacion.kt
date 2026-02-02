package com.example.mascotas.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mascotas.ui.screen.petDetail.PetDetailScreen
import com.example.mascotas.ui.screen.petDetail.PetDetailViewModel
import com.example.mascotas.ui.screen.petsList.PetsListScreen
import com.example.mascotas.ui.screen.petsList.PetsViewModel
import com.example.mascotas.ui.screen.razaList.RazaListScreen
import com.example.mascotas.ui.screen.razaList.RazaListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavegacionApp(
    petsViewModel: PetsViewModel,
    petDetailViewModel : PetDetailViewModel,
    razaListViewModel: RazaListViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Título dinámico según la ruta
                    val title = when {
                        currentDestination?.hasRoute<ScreenPetsList>() == true -> "Mis Mascotas"
                        currentDestination?.hasRoute<ScreenRazasList>() == true -> "Gestión de Razas"
                        currentDestination?.hasRoute<ScreenPetDetailSerialize>() == true -> "Detalle de Mascota"
                        else -> "Mascotas App"
                    }
                    Text(title, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    // Mostramos la flecha solo si NO estamos en una pantalla principal
                    if (currentDestination?.hasRoute<ScreenPetsList>() == false &&
                        currentDestination?.hasRoute<ScreenRazasList>() == false) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (currentDestination?.hasRoute<ScreenPetsList>() == true ||
                currentDestination?.hasRoute<ScreenRazasList>() == true) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentDestination.hasRoute<ScreenPetsList>(),
                        onClick = { navController.navigate(ScreenPetsList) },
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text("Mascotas") }
                    )
                    NavigationBarItem(
                        selected = currentDestination.hasRoute<ScreenRazasList>(),
                        onClick = { navController.navigate(ScreenRazasList) },
                        icon = { Icon(Icons.Default.List, contentDescription = null) },
                        label = { Text("Razas") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenPetsList,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<ScreenPetsList> {
                PetsListScreen(petsViewModel = petsViewModel, navigate = { id ->
                    navController.navigate(ScreenPetDetailSerialize(id))
                })
            }
            composable<ScreenRazasList> {
                RazaListScreen(razaListViewModel = razaListViewModel)
            }
            composable<ScreenPetDetailSerialize> { backStackEntry ->
                val id = backStackEntry.toRoute<ScreenPetDetailSerialize>().idMascota
                PetDetailScreen(petDetailViewModel = petDetailViewModel, idMascota = id)
            }
        }
    }
}