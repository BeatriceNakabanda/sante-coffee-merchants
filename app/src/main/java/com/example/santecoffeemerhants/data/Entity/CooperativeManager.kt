package com.example.santecoffeemerhants.data.Entity

import androidx.room.*
import com.example.santecoffeemerhants.data.converter.Converters
import java.io.Serializable
import java.util.*

@Entity(tableName = "cooperative_Manager", foreignKeys = [ForeignKey(entity = RegionalManager::class,
parentColumns = arrayOf("id"),
childColumns = arrayOf("regional_manager_id"),
onDelete = ForeignKey.CASCADE)], indices = [Index("regional_manager_id")])
data class CooperativeManager(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val cooperative_manager_id: Int = 0,
    @ColumnInfo(name = "regional_manager_id") val regional_manager_id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "gender") var gender: Int,
    @ColumnInfo(name = "phone_number") var phone_number: String,
    @ColumnInfo(name = "email") var email: String,
    @TypeConverters(Converters::class)
    var createdAt: Date?
) : Serializable