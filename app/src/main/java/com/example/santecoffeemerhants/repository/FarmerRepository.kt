package com.example.santecoffeemerhants.repository

import androidx.lifecycle.LiveData
import com.example.santecoffeemerhants.data.Dao.FarmerDao
import com.example.santecoffeemerhants.data.Entity.Farmer
import java.util.*


class FarmerRepository constructor(private val farmerDao: FarmerDao) {
    val allFarmers: LiveData<List<Farmer>> = farmerDao.getAllFarmers()

//    fun insertNewFarmer(farmer: Farmer): Farmer{
//        farmerDao.insert(farmer)
//        return farmer
//    }
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
    fun getSingleFarmer(farmerId: Int?): Farmer{
        val farmer = farmerDao.getSingleFarmer(farmerId)
        return farmer
    }
    fun getFarmerByDateAndTimeCreated(createdAt: Date?): Farmer{
        val farmer = farmerDao.getFarmerByDateAndTimeCreated(createdAt)
        return farmer
    }

}