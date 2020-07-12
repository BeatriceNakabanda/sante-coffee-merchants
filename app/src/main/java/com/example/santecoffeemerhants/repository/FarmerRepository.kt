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
    fun getFarmerByDateAndTimeCreated(createdAt: Date?): Farmer{
        val farmer = farmerDao.getFarmerByDateAndTimeCreated(createdAt)
        return farmer
    }
    fun getFarmerByPhoneNumber(phone_number: String): Farmer{
        val farmer = farmerDao.getFarmerByPhoneNumber(phone_number)
        return farmer
    }

}