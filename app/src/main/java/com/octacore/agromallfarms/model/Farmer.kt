package com.octacore.agromallfarms.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Farmer(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val firstName: String,
    val lastName: String,
    val otherNames: String,
    val profilePhoto: String,
    val phoneNumber: String,
    val birthday: String,
    val email: String,
    @Embedded val farm: Farm
)