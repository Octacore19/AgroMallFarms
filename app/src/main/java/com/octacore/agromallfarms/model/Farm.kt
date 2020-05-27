package com.octacore.agromallfarms.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farm")
data class Farm(
    @PrimaryKey var farmId: Long,
    var farmerFarmId: Long = 0,
    val name: String,
    val location: String,
    val latitude: String,
    val longitude: String
)