package com.example.mascotas.api.model

data class Mascota(
    val id: Int,
    val nombre: String,
    val edad: Int,
    val raza_id: Int,
    val raza_obj: Raza
)
