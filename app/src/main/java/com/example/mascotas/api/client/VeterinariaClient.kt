package com.example.mascotas.api.client

import com.example.mascotas.api.model.Especie
import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.model.Raza
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VeterinariaClient {
    @GET("mascota/")
    suspend fun obtenerListadoMascotas(): Response<List<Mascota>>

    @GET("raza/especies/")
    suspend fun obtenetListadoEspecies(): Response<List<Especie>>

    @GET("raza/especie/{especie}/")
    suspend fun obtenerRazasPorEspecie(@Path("especie") especie: String) : Response<List<Raza>>
}