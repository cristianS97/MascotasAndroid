package com.example.mascotas.api.domain

import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.repository.VeterinariaRepository
import javax.inject.Inject

class ObtenerListadoMascotasUseCase @Inject constructor(private val veterinariaRepository: VeterinariaRepository) {
    suspend operator fun invoke() : List<Mascota> {
        return veterinariaRepository.obtenerListadoMascotas()
    }
}