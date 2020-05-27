package com.octacore.agromallfarms.ui.register

import android.app.Application
import androidx.lifecycle.*
import com.octacore.agromallfarms.Repository
import com.octacore.agromallfarms.data.FarmersDatabase
import com.octacore.agromallfarms.model.Farm
import com.octacore.agromallfarms.model.Farmer
import com.octacore.agromallfarms.model.FarmerAndFarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(app: Application) : AndroidViewModel(app) {
    private val repo: Repository
    val farmers: LiveData<List<Farmer>>
    val farms: LiveData<List<Farm>>
    var firstName = ""
    var lastName = ""
    var otherName = ""
    var phoneNumber = ""
    var email = ""
    var profilePhoto = ""
    var birthday = ""
    var farmName = ""
    var farmLocation = ""
    var latitude = ""
    var longitude = ""

    init {
        val farmerDao = FarmersDatabase.getDatabase(app).farmerDao()
        repo = Repository(farmerDao)
        farmers = repo.farmers
        farms = repo.farms
    }

    fun registerFarmer(farmer: Farmer) = viewModelScope.launch(Dispatchers.IO) {
        repo.insertFarmer(farmer)
    }
}