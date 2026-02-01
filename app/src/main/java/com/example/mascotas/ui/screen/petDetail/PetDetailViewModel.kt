package com.example.mascotas.ui.screen.petDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mascotas.api.domain.mascota.ActualizarMascotaUseCase
import com.example.mascotas.api.domain.mascota.ObtenerMascotaPorIdUseCase
import com.example.mascotas.api.domain.raza.ObtenerListadoEspeciesUseCase
import com.example.mascotas.api.domain.raza.ObtenerRazasPorEspecieUseCase
import com.example.mascotas.api.model.Especie
import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.model.MascotaRequest
import com.example.mascotas.api.model.Raza
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetDetailViewModel @Inject constructor(
    private val obtenerMascotaPorIdUseCase: ObtenerMascotaPorIdUseCase,
    private val obtenerListadoEspeciesUseCase: ObtenerListadoEspeciesUseCase,
    private val obtenerRazasPorEspecieUseCase: ObtenerRazasPorEspecieUseCase,
    private val actualizarMascotaUseCase: ActualizarMascotaUseCase
) : ViewModel() {
    private val _mascota = MutableLiveData<Mascota?>()
    val mascota : LiveData<Mascota?> = _mascota

    private val _mascotaEditable = MutableLiveData<Mascota?>()
    val mascotaEditable: LiveData<Mascota?> = _mascotaEditable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isEditing = MutableLiveData<Boolean>()
    val isEditing: LiveData<Boolean> = _isEditing

    private val _especies = MutableLiveData<List<Especie>>()
    val especies : LiveData<List<Especie>> = _especies

    private val _razas = MutableLiveData<List<Raza>>()
    val razas : LiveData<List<Raza>> = _razas

    fun obtenerMascota(idMascota: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val mascotaResponse = obtenerMascotaPorIdUseCase(idMascota)
            if(mascotaResponse.id >= 0) {
                Log.i("Mascota", "La mascota se llama ${mascotaResponse.nombre}")
                _mascota.value = mascotaResponse
            } else {
                Log.i("Mascota", "No se ha podido obtener la información de la mascota")
            }
            _isLoading.value = false
        }
    }

    fun startEditing() {
        val actual = _mascota.value ?: return
        _mascotaEditable.value = actual.copy()
        _isEditing.value = true

        viewModelScope.launch {
            _especies.value = obtenerListadoEspeciesUseCase()
            _razas.value = obtenerRazasPorEspecieUseCase(actual.raza_obj.especie)
        }
    }

    fun cancelEditing() {
        _isEditing.value = false
        _mascotaEditable.value = null
    }

    fun onMascotaChange(updatedMascota: Mascota) {
        _mascotaEditable.value = updatedMascota
    }

    fun seleccionarEspecie(especie: String) {
        val actual = _mascotaEditable.value ?: return

        _mascotaEditable.value = actual.copy(
            raza_obj = actual.raza_obj.copy(
                especie = especie,
                raza = "Seleccione raza"
            )
        )

        viewModelScope.launch {
            _razas.value = obtenerRazasPorEspecieUseCase(especie)
        }
    }

    fun seleccionarRaza(raza: Raza) {
        val actual = _mascotaEditable.value ?: return

        _mascotaEditable.value = actual.copy(
            raza_id = raza.id,
            raza_obj = raza
        )
    }

    fun guardarCambios() {
        viewModelScope.launch {
            _mascota.value = _mascotaEditable.value
            actualizarMascotaUseCase(_mascota.value?.id ?: 0, MascotaRequest(nombre = _mascotaEditable.value?.nombre ?: "", edad = _mascotaEditable.value?.edad ?: 0, raza_id = _mascotaEditable.value?.raza_id ?: 0))
            _isEditing.value = false
        }
    }
}