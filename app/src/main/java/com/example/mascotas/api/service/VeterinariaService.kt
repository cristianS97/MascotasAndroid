package com.example.mascotas.api.service

import com.example.mascotas.api.client.VeterinariaClient
import com.example.mascotas.api.model.Mascota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VeterinariaService @Inject constructor(private val veterinariaClient: VeterinariaClient) {
    suspend fun obtenerListadoMascotas() : List<Mascota> {
        return withContext(Dispatchers.IO) {
            val response = veterinariaClient.obtenerListadoMascotas()
            response.body() as List<Mascota>
        }
    }
}