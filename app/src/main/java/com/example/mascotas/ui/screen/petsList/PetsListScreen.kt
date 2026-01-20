package com.example.mascotas.ui.screen.petsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mascotas.api.model.Mascota

@Composable
fun PetsListScreen(petsViewModel : PetsViewModel) {
    val isLoading : Boolean by petsViewModel.isLoading.observeAsState(initial = false)
    val mascotas : List<Mascota> by petsViewModel.mascotas.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        petsViewModel.obtenerListadoMascotas()
    }

    Scaffold(
        bottomBar = { PetsListScreenBottomBar(petsViewModel = petsViewModel) }
    ) { innerPadding ->
        Column(
            Modifier.padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PetsListScreenTitle()
            PetsListScreenListadoMascotas(isLoading = isLoading, mascotas = mascotas)
        }
    }
}

@Composable
fun PetsListScreenTitle() {
    Text(
        "Listado de mascotas",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun PetsListScreenListadoMascotas(isLoading: Boolean, mascotas: List<Mascota>) {
    if(isLoading) {
        CircularProgressIndicator()
    } else {
        if (mascotas.size == 0) {
            Text("No se han encontrado mascotas")
        } else {
            mascotas.forEach { mascota ->
                PetsListScreenDetalleMascota(mascota = mascota)
            }
        }
    }
}

@Composable
fun PetsListScreenDetalleMascota(mascota: Mascota) {
    Card(Modifier.padding(4.dp)) {
        Column(Modifier.padding(8.dp).fillMaxWidth(0.7f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(mascota.nombre)
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null,
                    modifier = Modifier.clickable(enabled = true, onClick = {})
                )
            }
            Text("${mascota.raza_obj.especie} - ${mascota.raza_obj.raza}")
        }
    }
}

@Composable
fun PetsListScreenBottomBar(petsViewModel: PetsViewModel) {
    BottomAppBar() {
        Row(Modifier.fillMaxWidth()) {
            Button(onClick = {
                petsViewModel.obtenerListadoMascotas()
            }) {
                Icon(Icons.Default.Refresh, contentDescription = null)
            }
            Button(onClick = {}) {
                Icon(Icons.Default.AddCircle, contentDescription = null)
            }
        }
    }
}
