package com.example.mascotas.api.client

import com.example.mascotas.api.model.Mascota
import retrofit2.Response
import retrofit2.http.GET

interface VeterinariaClient {
    @GET("mascota/")
    suspend fun obtenerListadoMascotas(): Response<List<Mascota>>
}