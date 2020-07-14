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
    fun insert(farmer: Farmer){
        repository.insertNewFarmer(farmer)
    }
//    fun updateSingleFarmer(farmerId: Int){
//        repository.updateSingleFarmer(farmerId)
//    }
    fun updateFarmer(farmer: Farmer){
        repository.updateFarmer(farmer)
    }
    fun getFarmerByDateAndTimeCreated(createdAt: Date?): Farmer{
        return repository.getFarmerByDateAndTimeCreated(createdAt)
    }
    fun getFarmerByPhoneNumber(phone_number: String): Farmer{
        return repository.getFarmerByPhoneNumber(phone_number)
    }
    fun getAllFarmersByRegionalManagerId(regionalManagerId: Int): LiveData<List<Farmer>> {
        return repository.getAllFarmersByRegionalManagerId(regionalManagerId)
    }
    fun getFarmerAndRegionalManager(regionalManagerId: Int, farmerId: Int): Farmer{
        return repository.getFarmerAndRegionalManager(regionalManagerId, farmerId)
    }

}