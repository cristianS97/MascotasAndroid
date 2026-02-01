package com.example.mascotas.api.client

import com.example.mascotas.api.model.Especie
import com.example.mascotas.api.model.Mascota
import com.example.mascotas.api.model.MascotaRequest
import com.example.mascotas.api.model.Raza
import com.example.mascotas.api.model.RazaRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VeterinariaClient {
    @GET("mascota/")
    suspend fun obtenerListadoMascotas(): Response<List<Mascota>>

    @GET("mascota/{id}/")
    suspend fun obtenerMascota(@Path("id") id : Int) : Response<Mascota>

    @POST("mascota/")
    suspend fun registrarMascota(@Body mascotaRequest: MascotaRequest) : Response<Unit>

    @DELETE("mascota/{id}/")
    suspend fun eliminarMascota(@Path("id") id : Int) : Response<Unit>

    @PUT("mascota/{id}/")
    suspend fun actualizarMascota(@Path("id") id : Int, @Body mascotaRequest: MascotaRequest) : Response<Unit>

    @GET("raza/especies/")
    suspend fun obtenetListadoEspecies(): Response<List<Especie>>

    @GET("raza/especie/{especie}/")
    suspend fun obtenerRazasPorEspecie(@Path("especie") especie: String) : Response<List<Raza>>

    @GET("raza/")
    suspend fun obtenerListadoRazas() : Response<List<Raza>>

    @POST("raza/")
    suspend fun registrarRaza(@Body razaRequest: RazaRequest) : Response<Unit>

    @GET("raza/{id}/")
    suspend fun obtenerRaza(@Path("id") id : Int) : Response<Raza>

    @PUT("raza/{id}/")
    suspend fun actualizarRaza(@Path("id") id : Int, @Body razaRequest: RazaRequest) : Response<Unit>

    @DELETE("raza/{id}/")
    suspend fun eliminarRaza(@Path("id") id : Int) : Response<Unit>
}