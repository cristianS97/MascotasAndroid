package com.example.mascotas.ui.screen.petDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mascotas.api.domain.mascota.ObtenerMascotaPorIdUseCase
import com.example.mascotas.api.model.Mascota
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetDetailViewModel @Inject constructor(
    private val obtenerMascotaPorIdUseCase: ObtenerMascotaPorIdUseCase
) : ViewModel() {
    private val _mascota = MutableLiveData<Mascota?>()
    val mascota : LiveData<Mascota?> = _mascota

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

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
}