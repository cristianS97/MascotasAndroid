package com.example.mascotas.api.domain.raza

import com.example.mascotas.api.model.Raza
import com.example.mascotas.api.repository.VeterinariaRepository
import javax.inject.Inject

class ObtenerRazasPorEspecieUseCase @Inject constructor(private val veterinariaRepository: VeterinariaRepository) {
    suspend operator fun invoke(especie: String) : List<Raza> {
        return veterinariaRepository.obtenerRazasPorEspecie(especie)
    }
}