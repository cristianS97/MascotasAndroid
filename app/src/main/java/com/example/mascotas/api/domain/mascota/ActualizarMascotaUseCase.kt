package com.example.mascotas.api.domain.mascota

import com.example.mascotas.api.model.MascotaRequest
import com.example.mascotas.api.repository.VeterinariaRepository
import javax.inject.Inject

class ActualizarMascotaUseCase @Inject constructor(private val veterinariaRepository: VeterinariaRepository) {
    suspend operator fun invoke(id: Int, mascotaRequest: MascotaRequest) {
        return veterinariaRepository.actualizarMascota(id, mascotaRequest)
    }
}