package com.example.mascotas.ui.screen.petsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mascotas.api.domain.ObtenerListadoMascotasUseCase
import com.example.mascotas.api.model.Mascota
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PetsViewModel @Inject constructor(
    private val obtenerListadoMascotasUseCase: ObtenerListadoMascotasUseCase
) : ViewModel() {
    private val _mascotas = MutableLiveData<List<Mascota>>()
    val mascotas : LiveData<List<Mascota>> = _mascotas

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

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
}