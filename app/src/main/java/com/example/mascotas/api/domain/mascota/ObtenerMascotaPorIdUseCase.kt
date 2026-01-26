package com.example.mascotas.api.domain.mascota

import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.repository.VeterinariaRepository
import javax.inject.Inject

class ObtenerMascotaPorIdUseCase @Inject constructor(private val veterinariaRepository: VeterinariaRepository) {
    suspend operator fun invoke(idMascota: Int) : Mascota {
        return veterinariaRepository.obtenerMascota(idMascota)
    }
}