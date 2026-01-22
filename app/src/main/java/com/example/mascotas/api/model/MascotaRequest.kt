package com.example.mascotas.api.model

data class MascotaRequest(
    val nombre: String,
    val edad: Int,
    val raza_id: Int
)
