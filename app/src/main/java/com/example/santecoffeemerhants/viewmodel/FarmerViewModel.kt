package com.example.santecoffeemerhants.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.santecoffeemerhants.data.Entity.Farmer
import com.example.santecoffeemerhants.data.SanteRoomDatabase
import com.example.santecoffeemerhants.repository.FarmerRepository
import java.util.*

class FarmerViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FarmerRepository
    val allFarmers: LiveData<List<Farmer>>

    init {
        val farmerDao = SanteRoomDatabase.getDatabase(application).farmerDao()
        repository = FarmerRepository(farmerDao)
        allFarmers = repository.allFarmers
    }
//    fun insert(farmer: Farmer): Farmer{
//        return repository.insertNewFarmer(farmer)
//    }
    fun insert(farmer: Farmer){
         repository.insertNewFarmer(farmer)
    }
    fun getFarmerByDateAndTimeCreated(createdAt: Date?): Farmer?{
        return repository.getFarmerByDateAndTimeCreated(createdAt)
    }
    fun getSingleFarmer(farmerId: Int?): Farmer{
        return repository.getSingleFarmer(farmerId)
    }
    fun updateFarmer(farmer: Farmer?){
        farmer?.let { repository.updateFarmer(it) }
    }

    fun getAllFarmersByRegionalManagerId(regionalManagerId: Int): LiveData<List<Farmer>> {
        return repository.getAllFarmersByRegionalManagerId(regionalManagerId)
    }


}