package com.example.mascotas.api.repository

import com.example.mascotas.api.model.Especie
import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.model.Raza
import com.example.mascotas.api.service.VeterinariaService
import javax.inject.Inject

class VeterinariaRepository @Inject constructor(private val veterinariaService: VeterinariaService) {
    suspend fun obtenerListadoMascotas() : List<Mascota> {
        return veterinariaService.obtenerListadoMascotas()
    }

    suspend fun obtenerListadoEspecies() : List<Especie> {
        return veterinariaService.obtenerListadoEspecies()
    }

    suspend fun obtenerRazasPorEspecie(especie: String) : List<Raza> {
        return veterinariaService.obtenerRazasPorEspecie(especie)
    }
}