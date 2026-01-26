package com.example.mascotas.ui.screen.petsList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mascotas.api.model.Especie
import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.model.Raza

@Composable
fun PetsListScreen(petsViewModel : PetsViewModel, navigate: (Int) -> Unit) {
    val isLoading : Boolean by petsViewModel.isLoading.observeAsState(initial = false)
    val seleccionableEspecieExpanded : Boolean by petsViewModel.seleccionableEspecieExpanded.observeAsState(initial = false)
    val seleccionableRazaExpanded : Boolean by petsViewModel.seleccionableRazaExpanded.observeAsState(initial = false)
    val mostrarModalCreacion : Boolean by petsViewModel.mostrarModalCreacion.observeAsState(initial = false)
    val nombre : String by petsViewModel.nombre.observeAsState(initial = "")
    val edad : String by petsViewModel.edad.observeAsState(initial = "")

    val mascotas : List<Mascota> by petsViewModel.mascotas.observeAsState(initial = emptyList())
    val especies : List<Especie> by petsViewModel.especies.observeAsState(initial = emptyList())
    val razas : List<Raza> by petsViewModel.razas.observeAsState(initial = emptyList())

    val especieSeleccionada : String by petsViewModel.especieSeleccionada.observeAsState(initial = "")
    val razaSeleccionada : String by petsViewModel.razaSeleccionada.observeAsState(initial = "")

    LaunchedEffect(Unit) {
        petsViewModel.obtenerListadoMascotas()
        petsViewModel.obtenerListadoEspecies()
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
            PetsListScreenListadoMascotas(
                isLoading = isLoading,
                mascotas = mascotas,
                petsViewModel = petsViewModel,
                navigate = navigate
            )
            if(mostrarModalCreacion) {
                PetsListScreenModalRegistroMascota(
                    especies = especies,
                    seleccionableEspecieExpanded = seleccionableEspecieExpanded,
                    especieSeleccionada = especieSeleccionada,
                    razas = razas,
                    seleccionableRazaExpanded = seleccionableRazaExpanded,
                    razaSeleccionada = razaSeleccionada,
                    nombre=nombre,
                    edad=edad,
                    petsViewModel = petsViewModel
                )
            }
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
fun PetsListScreenListadoMascotas(
    isLoading: Boolean,
    mascotas: List<Mascota>,
    petsViewModel: PetsViewModel,
    navigate: (Int) -> Unit
) {
    if(isLoading) {
        CircularProgressIndicator()
    } else {
        if (mascotas.size == 0) {
            Text("No se han encontrado mascotas")
        } else {
            mascotas.forEach { mascota ->
                PetsListScreenDetalleMascota(
                    mascota = mascota,
                    petsViewModel = petsViewModel,
                    navigate = navigate
                )
            }
        }
    }
}

@Composable
fun PetsListScreenDetalleMascota(
    mascota: Mascota,
    petsViewModel: PetsViewModel,
    navigate: (Int) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    Card(Modifier.padding(4.dp)) {
        Column(Modifier.padding(8.dp).fillMaxWidth(0.7f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(mascota.nombre)
                Box {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Opciones",
                        modifier = Modifier.clickable { showMenu = true }
                    )
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = {
                                Row {
                                    Icon(Icons.Default.Edit, contentDescription = "")
                                    Text("Editar")
                                }
                            },
                            onClick = {
                                showMenu = false
                                navigate(mascota.id)
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Row {
                                    Icon(Icons.Default.Delete, contentDescription = "")
                                    Text("Eliminar")
                                }
                            },
                            onClick = {
                                showMenu = false
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
            Text("${mascota.raza_obj.especie} - ${mascota.raza_obj.raza}")
        }
        if (showDeleteDialog) {
            ConfirmDeleteDialog(
                nombre = mascota.nombre,
                onConfirm = {
                    showDeleteDialog = false
                    petsViewModel.eliminarMascota(mascota.id)
                },
                onDismiss = { showDeleteDialog = false }
            )
        }
    }
}

@Composable
fun ConfirmDeleteDialog(nombre: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar mascota") },
        text = { Text("¿Estás seguro de que deseas eliminar a $nombre? Esta acción no se puede deshacer.") },
        confirmButton = {
            Button(onClick = onConfirm) { Text("Eliminar") }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
fun PetsListScreenBottomBar(petsViewModel: PetsViewModel) {
    BottomAppBar() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                petsViewModel.obtenerListadoMascotas()
            }) {
                Icon(Icons.Default.Refresh, contentDescription = null)
            }
            Button(onClick = {
                petsViewModel.abrirCerrarModalCreacion()
            }) {
                Icon(Icons.Default.AddCircle, contentDescription = null)
            }
        }
    }
}

@Composable
fun PetsListScreenModalRegistroMascota(
    especies: List<Especie>,
    seleccionableEspecieExpanded: Boolean,
    especieSeleccionada: String,
    petsViewModel: PetsViewModel,
    razas: List<Raza>,
    seleccionableRazaExpanded: Boolean,
    razaSeleccionada: String,
    nombre: String,
    edad: String
) {
    Dialog(onDismissRequest = { petsViewModel.abrirCerrarModalCreacion() }) {
        Column(Modifier.background(Color.White.copy(alpha = 0.5f)).padding(16.dp)) {
            Spacer(Modifier.height(8.dp))
            Text("Registro de mascota")
            Spacer(Modifier.height(4.dp))
            PetsListScreenMascotaForm(
                especies = especies,
                seleccionableEspecieExpanded = seleccionableEspecieExpanded,
                especieSeleccionada = especieSeleccionada,
                razas = razas,
                seleccionableRazaExpanded = seleccionableRazaExpanded,
                razaSeleccionada = razaSeleccionada,
                nombre=nombre,
                edad=edad,
                petsViewModel = petsViewModel
            )
            Spacer(Modifier.height(4.dp))
            Button(
                onClick = { petsViewModel.registrarMascota() },
                enabled = petsViewModel.isButtonCrearEnabled(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar mascota")
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsListScreenMascotaForm(
    especies: List<Especie>,
    seleccionableEspecieExpanded: Boolean,
    especieSeleccionada: String,
    petsViewModel: PetsViewModel,
    razas: List<Raza>,
    seleccionableRazaExpanded: Boolean,
    razaSeleccionada: String,
    nombre: String,
    edad: String
) {
    TextField(
        value = nombre,
        onValueChange = { petsViewModel.registrarNombre(it) },
        label = { Text("Ingrese nombre de la mascota") },
        placeholder = { Text("Larry") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(4.dp))
    TextField(
        value = edad,
        onValueChange = { petsViewModel.registrarEdad(it) },
        label = { Text("Ingrese edad de la mascota") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = { Text("Ej: 3") },
        singleLine = true,
        suffix = { Text("años") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(4.dp))
    ExposedDropdownMenuBox(
        expanded = seleccionableEspecieExpanded,
        onExpandedChange = { petsViewModel.abrirCerrarSeleccionableEspecie() },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = especieSeleccionada,
            onValueChange = {},
            readOnly = true,
            label = { Text("Especie") },
            modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true).fillMaxWidth(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(seleccionableEspecieExpanded) }
        )
        DropdownMenu(expanded = seleccionableEspecieExpanded, onDismissRequest = { petsViewModel.abrirCerrarSeleccionableEspecie() }) {
            especies.forEach {
                DropdownMenuItem(
                    text = { Text(it.especie) },
                    onClick = {
                        petsViewModel.seleccionarEspecie(it.especie)
                    }
                )
            }
        }
    }
    Spacer(Modifier.height(4.dp))
    ExposedDropdownMenuBox(
        expanded = seleccionableRazaExpanded,
        onExpandedChange = { petsViewModel.abrirCerrarSeleccionableRaza() },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = razaSeleccionada,
            onValueChange = {},
            enabled = razas.isNotEmpty(),
            readOnly = true,
            label = { Text("Raza") },
            modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true).fillMaxWidth(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(seleccionableRazaExpanded) }
        )
        DropdownMenu(expanded = seleccionableRazaExpanded, onDismissRequest = { petsViewModel.abrirCerrarSeleccionableRaza() }) {
            razas.forEach {
                DropdownMenuItem(
                    text = { Text(it.raza) },
                    onClick = {
                        petsViewModel.seleccionarRaza(it.raza, it.id)
                    }
                )
            }
        }
    }
}
