package com.example.mascotas.ui.screen.razaList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mascotas.api.domain.raza.EliminarRazaUseCase
import com.example.mascotas.api.domain.raza.ObtenerListadoEspeciesUseCase
import com.example.mascotas.api.domain.raza.ObtenerRazasUseCase
import com.example.mascotas.api.domain.raza.RegistrarRazaUseCase
import com.example.mascotas.api.model.Especie
import com.example.mascotas.api.model.Raza
import com.example.mascotas.api.model.RazaRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RazaListViewModel @Inject constructor(
    private val obtenerRazasUseCase: ObtenerRazasUseCase,
    private val eliminarRazaUseCase: EliminarRazaUseCase,
    private val obtenerListadoEspeciesUseCase: ObtenerListadoEspeciesUseCase,
    private val registrarRazaUseCase: RegistrarRazaUseCase
) : ViewModel() {
    private val _razas = MutableLiveData<List<Raza>>()
    val razas : LiveData<List<Raza>> = _razas

    private val _especies = MutableLiveData<List<Especie>>()
    val especies: LiveData<List<Especie>> = _especies

    private val _nuevaEspecie = MutableLiveData<String>()
    val nuevaEspecie : LiveData<String> = _nuevaEspecie

    private val _nuevaRaza = MutableLiveData<String>()
    val nuevaRaza : LiveData<String> = _nuevaRaza

    private val _expandedEspecie = MutableLiveData(false)
    val expandedEspecie: LiveData<Boolean> = _expandedEspecie

    private val _errorEvento = MutableSharedFlow<String>()
    val errorEvento = _errorEvento.asSharedFlow()

    fun obtenerListadoRazas() {
        viewModelScope.launch {
            _especies.value = obtenerListadoEspeciesUseCase()
            _razas.value = obtenerRazasUseCase()
        }
    }

    fun crearRaza() {
        viewModelScope.launch {
            val especieStr = _nuevaEspecie.value ?: ""
            val razaStr = _nuevaRaza.value ?: ""
            if (especieStr.isNotBlank() && razaStr.isNotBlank()) {
                registrarRazaUseCase(RazaRequest(especieStr, razaStr))
                _nuevaEspecie.value = ""
                _nuevaRaza.value = ""
                obtenerListadoRazas() // Refrescamos todo
            }
        }
    }

    fun setExpandedEspecie(expanded: Boolean) {
        _expandedEspecie.value = expanded
    }

    fun eliminarRaza(id : Int) {
        viewModelScope.launch {
            eliminarRazaUseCase(id)
            obtenerListadoRazas()
        }
    }

    fun rellenaNuevaEspecie(especie: String) {
        _nuevaEspecie.value = especie
    }

    fun rellenaNuevaRaza(raza: String) {
        _nuevaRaza.value = raza
    }
}