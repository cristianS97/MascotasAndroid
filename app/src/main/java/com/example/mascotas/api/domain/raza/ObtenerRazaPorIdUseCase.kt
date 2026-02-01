package com.example.mascotas.api.domain.raza

import com.example.mascotas.api.model.Raza
import com.example.mascotas.api.repository.VeterinariaRepository
import javax.inject.Inject

class ObtenerRazaPorIdUseCase @Inject constructor(private val veterinariaRepository: VeterinariaRepository) {
    suspend operator fun invoke(id : Int) : Raza {
        return veterinariaRepository.obtenerRaza(id)
    }
}
