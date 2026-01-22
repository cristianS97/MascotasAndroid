package com.example.mascotas.ui.screen.petsList

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mascotas.api.domain.mascota.ObtenerListadoMascotasUseCase
import com.example.mascotas.api.domain.raza.EliminarMascotaUseCase
import com.example.mascotas.api.domain.raza.ObtenerListadoEspeciesUseCase
import com.example.mascotas.api.domain.raza.ObtenerRazasUseCase
import com.example.mascotas.api.domain.raza.RegistrarMascotaUseCase
import com.example.mascotas.api.model.Especie
import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.model.MascotaRequest
import com.example.mascotas.api.model.Raza
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PetsViewModel @Inject constructor(
    private val obtenerListadoMascotasUseCase: ObtenerListadoMascotasUseCase,
    private val registrarMascotaUseCase: RegistrarMascotaUseCase,
    private val eliminarMascotaUseCase: EliminarMascotaUseCase,
    private val obtenerListadoEspeciesUseCase: ObtenerListadoEspeciesUseCase,
    private val obtenerRazasUseCase: ObtenerRazasUseCase
) : ViewModel() {
    private val _mascotas = MutableLiveData<List<Mascota>>()
    val mascotas : LiveData<List<Mascota>> = _mascotas

    private val _especies = MutableLiveData<List<Especie>>()
    val especies : LiveData<List<Especie>> = _especies

    private val _razas = MutableLiveData<List<Raza>>()
    val razas : LiveData<List<Raza>> = _razas

    private val _especieSeleccionada = MutableLiveData<String>()
    val especieSeleccionada : LiveData<String> = _especieSeleccionada

    private val _razaSeleccionada = MutableLiveData<String>()
    val razaSeleccionada : LiveData<String> = _razaSeleccionada
    
    private val _idRazaSeleccionada = MutableLiveData<Int>()

    private val _nombre = MutableLiveData<String>()
    val nombre : LiveData<String> = _nombre

    private val _edad = MutableLiveData<String>()
    val edad : LiveData<String> = _edad

    private val _seleccionableEspecieExpanded = MutableLiveData<Boolean>()
    val seleccionableEspecieExpanded : LiveData<Boolean> = _seleccionableEspecieExpanded

    private val _seleccionableRazaExpanded = MutableLiveData<Boolean>()
    val seleccionableRazaExpanded : LiveData<Boolean> = _seleccionableRazaExpanded

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _mostrarModalCreacion = MutableLiveData<Boolean>()
    val mostrarModalCreacion : LiveData<Boolean> = _mostrarModalCreacion

    fun obtenerListadoMascotas() {
        viewModelScope.launch {
            _isLoading.value = true
            val mascotasResponse : List<Mascota> = obtenerListadoMascotasUseCase()
            if(mascotasResponse.size > 0) {
                Log.i("Mascotas", "Se han obtenido ${mascotasResponse.size} mascotas")
                _mascotas.value = mascotasResponse
            } else {
                Log.i("Mascotas", "Error al obtener las mascotas")
            }
            _isLoading.value = false
        }
    }

    fun obtenerListadoEspecies() {
        viewModelScope.launch {
            val especiesResponse : List<Especie> = obtenerListadoEspeciesUseCase()
            _especies.value = especiesResponse
        }
    }

    fun seleccionarEspecie(especieSeleccion: String) {
        _especieSeleccionada.value = especieSeleccion
        _seleccionableEspecieExpanded.value = false
        if(especieSeleccion.isNotBlank() && especieSeleccion.isNotEmpty()) {
            viewModelScope.launch {
                Log.i("Especies", "Obteniendo especies")
                _razas.value = obtenerRazasUseCase(especieSeleccion)
                Log.i("Especies", "Se han obtenido ${_razas.value?.size} especies")
            }
        }
    }

    fun seleccionarRaza(razaSeleccion: String, idRaza: Int) {
        _razaSeleccionada.value = razaSeleccion
        _idRazaSeleccionada.value = idRaza
        _seleccionableRazaExpanded.value = false
    }

    fun registrarNombre(nombre: String) {
        _nombre.value = nombre
    }

    fun registrarEdad(edad: String) {
        if(edad.isEmpty() || edad.isDigitsOnly()) {
            _edad.value = edad
        }
    }

    fun abrirCerrarSeleccionableEspecie() {
        _seleccionableEspecieExpanded.value = !(_seleccionableEspecieExpanded.value ?: false)
    }

    fun abrirCerrarSeleccionableRaza() {
        _seleccionableRazaExpanded.value = !(_seleccionableRazaExpanded.value ?: false)
    }

    fun abrirCerrarModalCreacion() {
        _especieSeleccionada.value = ""
        _razaSeleccionada.value = ""
        _nombre.value = ""
        _mostrarModalCreacion.value = !(_mostrarModalCreacion.value ?: false)
    }

    fun isButtonCrearEnabled(): Boolean {
        val nombreValido = !(_nombre.value.isNullOrBlank()) && (_nombre.value?.length ?: 0) >= 2
        val especieValida = !(_especieSeleccionada.value.isNullOrBlank())
        val razaValida = !(_razaSeleccionada.value.isNullOrBlank())

        val edadString = _edad.value ?: ""
        val edadValida = edadString.isDigitsOnly() && (edadString.toIntOrNull() ?: 0) > 0

        return nombreValido && especieValida && razaValida && edadValida
    }

    private fun limpiarFormulario() {
        _nombre.value = ""
        _edad.value = ""
        _especieSeleccionada.value = ""
        _razaSeleccionada.value = ""
        _idRazaSeleccionada.value = 0
    }

    fun registrarMascota() {
        val mascotaRequest : MascotaRequest = MascotaRequest(
            nombre = _nombre.value ?: "",
            edad = _edad.value?.toIntOrNull() ?: 0,
            raza_id = _idRazaSeleccionada.value?.toInt() ?: 0
        )
        viewModelScope.launch {
            registrarMascotaUseCase(mascotaRequest)
            obtenerListadoMascotas()
            limpiarFormulario()
        }
        _mostrarModalCreacion.value = false
    }

    fun eliminarMascota(idMascota: Int) {
        viewModelScope.launch {
            eliminarMascotaUseCase(idMascota)
            obtenerListadoMascotas()
        }
    }
}