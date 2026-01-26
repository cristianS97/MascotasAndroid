package com.example.mascotas.api.domain.mascota

import com.example.mascotas.api.model.MascotaRequest
import com.example.mascotas.api.repository.VeterinariaRepository
import javax.inject.Inject

class RegistrarMascotaUseCase @Inject constructor(private val veterinariaRepository: VeterinariaRepository) {
    suspend operator fun invoke(mascotaRequest: MascotaRequest) {
        return veterinariaRepository.registrarMascota(mascotaRequest)
    }
}