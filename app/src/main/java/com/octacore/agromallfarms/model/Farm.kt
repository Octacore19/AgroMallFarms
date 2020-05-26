package com.octacore.agromallfarms.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Farm(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val farmerId: Int,
    val name: String,
    val location: String,
    val latitude: Float,
    val longitude: Float
)