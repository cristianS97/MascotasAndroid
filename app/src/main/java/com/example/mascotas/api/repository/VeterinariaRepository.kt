package com.example.mascotas.api.repository

import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.service.VeterinariaService
import javax.inject.Inject

class VeterinariaRepository @Inject constructor(private val veterinariaService: VeterinariaService) {
    suspend fun obtenerListadoMascotas() : List<Mascota> {
        return veterinariaService.obtenerListadoMascotas()
    }
}