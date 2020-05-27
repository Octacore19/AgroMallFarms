package com.octacore.agromallfarms.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.octacore.agromallfarms.Repository
import com.octacore.agromallfarms.data.FarmersDatabase
import com.octacore.agromallfarms.model.Farm

class MapViewModel(app: Application): AndroidViewModel(app) {
    private val repo: Repository
    val farms: LiveData<List<Farm>>

    init {
        val farmerDao = FarmersDatabase.getDatabase(app).farmerDao()
        repo = Repository((farmerDao))
        farms = repo.farms
    }
}