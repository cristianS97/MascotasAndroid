package com.example.mascotas.ui.screen.petDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import com.example.mascotas.api.model.Mascota

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailScreen(petDetailViewModel: PetDetailViewModel, idMascota: Int) {
    val isLoading : Boolean by petDetailViewModel.isLoading.observeAsState(initial = false)
    val isEditing : Boolean by petDetailViewModel.isEditing.observeAsState(initial = false)
    val mascota : Mascota? by petDetailViewModel.mascota.observeAsState(initial = null)
    val mascotaEditable : Mascota? by petDetailViewModel.mascotaEditable.observeAsState(initial = null)
    val especies by petDetailViewModel.especies.observeAsState(initial = emptyList())
    val razas by petDetailViewModel.razas.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        petDetailViewModel.obtenerMascota(idMascota)
    }

    val displayData = if (isEditing) mascotaEditable else mascota

    Scaffold(
        floatingActionButton = {
            if (displayData != null) {
                FloatingActionButton(onClick = {
                    if (isEditing) petDetailViewModel.guardarCambios()
                    else petDetailViewModel.startEditing()
                }) {
                    Icon(if (isEditing) Icons.Default.Check else Icons.Default.Edit, contentDescription = null)
                }
            }
        }
    ) { innerPadding ->
        Column(
            Modifier.padding(innerPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(Modifier.padding(4.dp)) {
                Column(Modifier.padding(8.dp).fillMaxWidth(0.7f)) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    }

                    if (!isLoading && mascota == null) {
                        Text("No se ha encontrado la información de la mascota que busca")
                    } else {
                        displayData?.let { pet ->
                            val fields = listOf(
                                "Nombre" to pet.nombre,
                                "Edad" to pet.edad.toString()
                            )

                            fields.forEach { (label, value) ->
                                PetField(
                                    label = label,
                                    value = value,
                                    isEditing = isEditing,
                                    onValueChange = {
                                        val updatedPet = when (label) {
                                            "Nombre" -> pet.copy(nombre = it)
                                            "Edad" -> pet.copy(edad = it.toIntOrNull() ?: pet.edad)
                                            else -> pet
                                        }
                                        petDetailViewModel.onMascotaChange(updatedPet)
                                    }
                                )
                            }

                            // 2. Desplegable de Especie
                            Text("Especie", Modifier.padding(top = 8.dp))
                            if (isEditing) {
                                PetDropDown(
                                    label = "Especie",
                                    items = especies,
                                    selectedItemLabel = pet.raza_obj.especie,
                                    onItemClick = { petDetailViewModel.seleccionarEspecie(it.especie) }
                                ) { item ->
                                    Text(item.especie)
                                }
                            } else {
                                Text(
                                    pet.raza_obj.especie,
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                                )
                            }

                            // 3. Desplegable de Raza
                            Text("Raza", Modifier.padding(top = 16.dp))
                            if (isEditing) {
                                PetDropDown(
                                    label = "Raza",
                                    items = razas,
                                    enabled = razas.isNotEmpty(), // Se deshabilita si no hay especies cargadas
                                    selectedItemLabel = pet.raza_obj.raza,
                                    onItemClick = { petDetailViewModel.seleccionarRaza(it) }
                                ) { item ->
                                    Text(item.raza)
                                }
                            } else {
                                Text(
                                    pet.raza_obj.raza,
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                                )
                            }

                            if (isEditing) {
                                Button(
                                    onClick = { petDetailViewModel.cancelEditing() },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                ) {
                                    Text("Cancelar y Restaurar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PetField(label: String, value: String, isEditing: Boolean, onValueChange: (String) -> Unit) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text(text = label)
        if (isEditing) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(text = value)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PetDropDown(
    label: String,
    items: List<T>,
    selectedItemLabel: String,
    onItemClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    itemContent: @Composable (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(label, style = androidx.compose.material3.MaterialTheme.typography.labelMedium)

        ExposedDropdownMenuBox(
            expanded = expanded && enabled,
            onExpandedChange = { if (enabled) expanded = it }
        ) {
            OutlinedTextField(
                value = selectedItemLabel,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded && enabled,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { itemContent(item) },
                        onClick = {
                            onItemClick(item)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}