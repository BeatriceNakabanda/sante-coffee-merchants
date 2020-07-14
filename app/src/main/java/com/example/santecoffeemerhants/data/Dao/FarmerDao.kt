package com.example.santecoffeemerhants.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.santecoffeemerhants.data.Entity.Farmer
import java.util.*

@Dao
interface FarmerDao {
    @Query("SELECT * FROM FARMER_TABLE")
    fun getAllFarmers():LiveData<List<Farmer>>

    @Query("SELECT * FROM FARMER_TABLE WHERE farmer_table.manager_id LIKE :regional_manager_id")
    fun getAllFarmersByRegionalManagerId(regional_manager_id : Int):LiveData<List<Farmer>>

    @Query("SELECT * FROM farmer_table WHERE farmer_table.id LIKE :farmer_id")
    fun getSingleFarmer(farmer_id: Int): Farmer

    @Query("SELECT * FROM FARMER_TABLE WHERE manager_id LIKE :regional_manager_id and id LIKE :farmer_id")
    fun getFarmerAndRegionalManager(regional_manager_id : Int, farmer_id: Int): Farmer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(farmer: Farmer)

    @Query("SELECT * FROM farmer_table WHERE farmer_table.created_at LIKE :created_at")
    fun getFarmerByDateAndTimeCreated(created_at: Date?): Farmer

    @Query("SELECT * FROM farmer_table WHERE farmer_table.phone_number LIKE :phone_number")
    fun getFarmerByPhoneNumber(phone_number: String): Farmer

//    @Query("UPDATE farmer_table SET name = :name, gender = :gender, phone_number = :phone_number, birth_certificate = :birth_certificate, national_id = :national_id WHERE id :farmer_id" )
//    fun updateSingleFarmer(farmer_id: Int): Farmer

    @Update
    fun updateFarmer(farmer: Farmer)
}