package com.example.mascotas.api.domain.raza

import com.example.mascotas.api.model.RazaRequest
import com.example.mascotas.api.repository.VeterinariaRepository
import javax.inject.Inject

class RegistrarRazaUseCase @Inject constructor(private val veterinariaRepository: VeterinariaRepository) {
    suspend operator fun invoke(razaRequest: RazaRequest) {
        return veterinariaRepository.registrarRaza(razaRequest)
    }
}