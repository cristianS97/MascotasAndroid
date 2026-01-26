package com.example.mascotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.mascotas.ui.screen.NavegacionApp
import com.example.mascotas.ui.screen.petDetail.PetDetailViewModel
import com.example.mascotas.ui.screen.petsList.PetsListScreen
import com.example.mascotas.ui.screen.petsList.PetsViewModel
import com.example.mascotas.ui.theme.MascotasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val petsViewModel : PetsViewModel by viewModels()
    val petDetailViewModel : PetDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MascotasTheme {
                NavegacionApp(
                    petsViewModel = petsViewModel,
                    petDetailViewModel = petDetailViewModel
                )
            }
        }
    }
}
