package com.example.santecoffeemerhants.repository

import androidx.lifecycle.LiveData
import com.example.santecoffeemerhants.data.Dao.FarmerDao
import com.example.santecoffeemerhants.data.Entity.Farmer
import java.util.*


class FarmerRepository constructor(private val farmerDao: FarmerDao) {
    val allFarmers: LiveData<List<Farmer>> = farmerDao.getAllFarmers()

    fun insertNewFarmer(farmer: Farmer){
        farmerDao.insert(farmer)
    }
    fun updateFarmer(farmer: Farmer){
        farmerDao.updateFarmer(farmer)
    }
    fun getAllFarmersByRegionalManagerId(regionalManagerId: Int): LiveData<List<Farmer>> {
        val farmer = farmerDao.getAllFarmersByRegionalManagerId(regionalManagerId)
        return farmer
    }
    fun getFarmerByDateAndTimeCreated(createdAt: Date?): Farmer{
        val farmer = farmerDao.getFarmerByDateAndTimeCreated(createdAt)
        return farmer
    }
    fun getFarmerByPhoneNumber(phone_number: String): Farmer{
        val farmer = farmerDao.getFarmerByPhoneNumber(phone_number)
        return farmer
    }
    fun getFarmerAndRegionalManager(regionalManagerId: Int, farmerId: Int): Farmer{
        val farmer = farmerDao.getFarmerAndRegionalManager(regionalManagerId, farmerId)
        return farmer
    }
//    fun updateSingleFarmer(farmerId: Int){
//        farmerDao.updateSingleFarmer(farmerId)
//    }

}