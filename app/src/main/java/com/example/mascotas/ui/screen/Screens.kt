package com.example.mascotas.ui.screen

import kotlinx.serialization.Serializable

@Serializable
object ScreenPetsList

@Serializable
data class ScreenPetDetailSerialize(val idMascota: Int)