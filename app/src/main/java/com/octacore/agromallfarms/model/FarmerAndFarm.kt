package com.octacore.agromallfarms.model

import androidx.room.Embedded
import androidx.room.Relation

data class FarmerAndFarm(
    @Embedded val farmer: Farmer,
    @Relation(
        parentColumn = "farmerId",
        entityColumn = "farmerFarmId"
    ) val farm: Farm
)