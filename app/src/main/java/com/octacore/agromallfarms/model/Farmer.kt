package com.octacore.agromallfarms.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "farmer")
data class Farmer(
    @PrimaryKey val farmerId: Long,
    val firstName: String,
    val lastName: String,
    val otherNames: String?,
    val profilePhoto: String?,
    val phoneNumber: String,
    val birthday: String,
    val email: String
){
    @Ignore lateinit var farm: Farm
}