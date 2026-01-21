package com.example.mascotas.api.domain.raza

import com.example.mascotas.api.model.Especie
import com.example.mascotas.api.repository.VeterinariaRepository
import javax.inject.Inject

class ObtenerListadoEspeciesUseCase @Inject constructor(private val veterinariaRepository: VeterinariaRepository) {
    suspend operator fun invoke() : List<Especie> {
        return veterinariaRepository.obtenerListadoEspecies()
    }
}