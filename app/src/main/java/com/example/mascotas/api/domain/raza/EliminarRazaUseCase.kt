package com.example.mascotas.api.domain.raza

import com.example.mascotas.api.repository.VeterinariaRepository
import javax.inject.Inject

class EliminarRazaUseCase @Inject constructor(private val veterinariaRepository: VeterinariaRepository) {
    suspend operator fun invoke(id : Int) {
        return veterinariaRepository.eliminarRaza(id)
    }
}