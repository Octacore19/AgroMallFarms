package com.octacore.agromallfarms.model

import androidx.room.Entity

@Entity
data class Farm(
    val name: String,
    val location: String,
    val latitude: Float,
    val longitude: Float
)