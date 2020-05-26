package com.octacore.agromallfarms

import com.octacore.agromallfarms.data.dao.FarmerDao
import com.octacore.agromallfarms.model.Farmer

class Repository(private val farmerDao: FarmerDao) {

    val farmers = farmerDao.getAllFarmers()

    suspend fun insertFarmer(farmer: Farmer){
        farmerDao.insertFarmer(farmer)
    }
}