package com.octacore.agromallfarms

import com.octacore.agromallfarms.data.dao.FarmerDao
import com.octacore.agromallfarms.model.Farm
import com.octacore.agromallfarms.model.Farmer

class Repository(private val farmerDao: FarmerDao) {

    val farmers = farmerDao.getAllFarmersAndTheirFarm()

    suspend fun insertFarmerAndFarm(farmer: Farmer, farm: Farm){
        farmerDao.insertFarmerAndFarm(farmer, farm)
    }
}