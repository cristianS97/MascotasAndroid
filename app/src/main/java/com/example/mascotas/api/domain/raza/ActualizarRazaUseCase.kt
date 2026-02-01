package com.example.mascotas.api.domain.raza

import com.example.mascotas.api.model.RazaRequest
import com.example.mascotas.api.repository.VeterinariaRepository
import javax.inject.Inject

class ActualizarRazaUseCase @Inject constructor(private val veterinariaRepository: VeterinariaRepository) {
    suspend operator fun invoke(id : Int, razaRequest: RazaRequest) {
        return veterinariaRepository.actualizarRaza(id, razaRequest)
    }
}