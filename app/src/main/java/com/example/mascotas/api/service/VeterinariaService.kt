package com.example.mascotas.api.service

import android.util.Log
import com.example.mascotas.api.client.VeterinariaClient
import com.example.mascotas.api.model.Especie
import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.model.MascotaRequest
import com.example.mascotas.api.model.Raza
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

    suspend fun obtenerMascota(idMascota: Int) : Mascota {
        return withContext(Dispatchers.IO) {
            val response = veterinariaClient.obtenerMascota(idMascota)
            response.body() as Mascota
        }
    }

    suspend fun registrarMascota(mascotaRequest: MascotaRequest) {
        return withContext(Dispatchers.IO) {
            val response = veterinariaClient.registrarMascota(mascotaRequest)
            response.isSuccessful
        }
    }

    suspend fun eliminarMascota(id: Int) {
        return withContext(Dispatchers.IO) {
            val response = veterinariaClient.eliminarMascota(id)
            response.isSuccessful
        }
    }

    suspend fun actualizarMascota(id: Int, mascotaRequest: MascotaRequest) {
        return withContext(Dispatchers.IO) {
            val response = veterinariaClient.actualizarMascota(id, mascotaRequest)
            response.isSuccessful
        }
    }

    suspend fun obtenerListadoEspecies(): List<Especie> {
        return withContext(Dispatchers.IO) {
            val response = veterinariaClient.obtenetListadoEspecies()
            response.body() as List<Especie>
        }
    }

    suspend fun obtenerRazasPorEspecie(especie: String): List<Raza> {
        return withContext(Dispatchers.IO) {
            val response = veterinariaClient.obtenerRazasPorEspecie(especie)
            response.body() as List<Raza>
        }
    }
}