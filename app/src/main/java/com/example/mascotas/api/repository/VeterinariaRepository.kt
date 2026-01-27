package com.example.mascotas.api.repository

import com.example.mascotas.api.model.Especie
import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.model.MascotaRequest
import com.example.mascotas.api.model.Raza
import com.example.mascotas.api.service.VeterinariaService
import javax.inject.Inject

class VeterinariaRepository @Inject constructor(private val veterinariaService: VeterinariaService) {
    suspend fun obtenerListadoMascotas() : List<Mascota> {
        return veterinariaService.obtenerListadoMascotas()
    }

    suspend fun obtenerMascota(idMascota: Int) : Mascota {
        return veterinariaService.obtenerMascota(idMascota)
    }

    suspend fun registrarMascota(mascotaRequest: MascotaRequest) : Unit {
        return veterinariaService.registrarMascota(mascotaRequest)
    }

    suspend fun eliminarMascota(id: Int) : Unit {
        return veterinariaService.eliminarMascota(id)
    }

    suspend fun actualizarMascota(id: Int, mascotaRequest: MascotaRequest) {
        return veterinariaService.actualizarMascota(id, mascotaRequest)
    }

    suspend fun obtenerListadoEspecies() : List<Especie> {
        return veterinariaService.obtenerListadoEspecies()
    }

    suspend fun obtenerRazasPorEspecie(especie: String) : List<Raza> {
        return veterinariaService.obtenerRazasPorEspecie(especie)
    }
}