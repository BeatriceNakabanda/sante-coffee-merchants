package com.example.santecoffeemerhants.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.santecoffeemerhants.data.Entity.Farmer
import java.util.*

@Dao
interface FarmerDao {
    @Query("SELECT * FROM FARMER_TABLE")
    fun getAllFarmers():LiveData<List<Farmer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(farmer: Farmer)

    @Query("SELECT * FROM farmer_table WHERE farmer_table.created_at LIKE :created_at")
    fun getFarmerByDateAndTimeCreated(created_at: Date?): Farmer

    @Query("SELECT * FROM farmer_table WHERE farmer_table.phone_number LIKE :phone_number")
    fun getFarmerByPhoneNumber(phone_number: String): Farmer
}