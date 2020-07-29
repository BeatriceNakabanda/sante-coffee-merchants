package com.example.santecoffeemerhants.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.santecoffeemerhants.data.Entity.CooperativeManager
import java.util.*

@Dao
interface CoopManagerDao {
    @Query("SELECT * FROM cooperative_Manager")
    fun allCooperativeManagers():LiveData<List<CooperativeManager>>

    @Query("SELECT * FROM cooperative_Manager WHERE regional_manager_id LIKE :regional_manager_id")
    fun getAllCooperativeManagersByRegionalManager(regional_manager_id: Int): LiveData<List<CooperativeManager>>

    @Query("SELECT * FROM cooperative_Manager WHERE createdAt LIKE :created_at")
    fun getCooperativeManagerByCreatedAt(created_at: Date?): CooperativeManager

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cooperativeManager: CooperativeManager)

    @Update
    fun updateFarmer(cooperativeManager: CooperativeManager)
}