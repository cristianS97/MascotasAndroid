package com.example.mascotas.ui.screen.petDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.mascotas.api.model.Mascota

@Composable
fun PetDetailScreen(petDetailViewModel: PetDetailViewModel, idMascota: Int) {
    val isLoading : Boolean by petDetailViewModel.isLoading.observeAsState(initial = false)
    val mascota : Mascota? by petDetailViewModel.mascota.observeAsState(initial = null)

    LaunchedEffect(Unit) {
        petDetailViewModel.obtenerMascota(idMascota)
    }

    Scaffold() { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            if(isLoading) {
                CircularProgressIndicator()
            }

            if(!isLoading && mascota == null) {
                Text("No se ha encontrado la información de la mascota que busca")
            } else {
                Text(mascota?.nombre ?: "")
                Text("${mascota?.edad ?: 0}")
                Text(mascota?.raza_obj?.especie ?: "")
                Text(mascota?.raza_obj?.raza ?: "")
            }
        }
    }
}