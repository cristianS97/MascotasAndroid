package com.example.mascotas.ui.screen.razaList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mascotas.api.model.Raza

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RazaListScreen(razaListViewModel: RazaListViewModel) {
    val razas by razaListViewModel.razas.observeAsState(emptyList())
    val especies by razaListViewModel.especies.observeAsState(emptyList())
    val nuevaEspecie by razaListViewModel.nuevaEspecie.observeAsState("")
    val nuevaRaza by razaListViewModel.nuevaRaza.observeAsState("")
    val expanded by razaListViewModel.expandedEspecie.observeAsState(false)
    var showDeleteDialog by remember { mutableStateOf(false) }
    var razaSeleccionada by remember { mutableStateOf<Raza?>(null) }

    LaunchedEffect(Unit) {
        razaListViewModel.obtenerListadoRazas()
    }

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { razaListViewModel.setExpandedEspecie(it) }
        ) {
            OutlinedTextField(
                value = nuevaEspecie,
                onValueChange = {
                    razaListViewModel.rellenaNuevaEspecie(it)
                    if (it.isNotEmpty()) razaListViewModel.setExpandedEspecie(true)
                },
                label = { Text("Especie (Escribe o selecciona)") },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable)
                    .fillMaxWidth(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )

            // Filtramos las especies que coincidan con lo que el usuario escribe
            val filteringOptions =
                especies.filter { it.especie.contains(nuevaEspecie, ignoreCase = true) }

            if (filteringOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { razaListViewModel.setExpandedEspecie(false) }
                ) {
                    filteringOptions.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion.especie) },
                            onClick = {
                                razaListViewModel.rellenaNuevaEspecie(opcion.especie)
                                razaListViewModel.setExpandedEspecie(false)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = nuevaRaza,
            onValueChange = { razaListViewModel.rellenaNuevaRaza(it) },
            label = { Text("Nombre de la Raza") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { razaListViewModel.crearRaza() },
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Añadir Raza")
        }

        Spacer(Modifier.padding(vertical = 16.dp))

        LazyColumn {
            items(razas) { raza ->
                Card(Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(raza.raza, fontWeight = FontWeight.Bold)
                            Text(raza.especie)
                        }
                        IconButton(onClick = {
                            razaSeleccionada = raza
                            showDeleteDialog = true
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog && razaSeleccionada != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar eliminación") },
            text = {
                Text("¿Estás seguro de que deseas eliminar la raza ${razaSeleccionada?.raza}? " +
                        "Esta acción no se puede deshacer.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        razaSeleccionada?.let { razaListViewModel.eliminarRaza(it.id) }
                        showDeleteDialog = false
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}