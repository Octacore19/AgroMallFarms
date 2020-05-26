package com.octacore.agromallfarms.model

data class Farmer(
    val firstName: String,
    val lastName: String,
    val otherNames: String,
    val profilePhoto: String,
    val phoneNumber: String,
    val birthday: String,
    val email: String,
    val farm: Farm
)