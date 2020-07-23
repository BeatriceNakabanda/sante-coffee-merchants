package com.example.santecoffeemerhants.data.Entity

import androidx.room.*
import com.example.santecoffeemerhants.data.converter.Converters
import java.io.Serializable
import java.util.*

@Entity(tableName = "farmer_table", foreignKeys = [ForeignKey(entity = RegionalManager::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("manager_id"),
    onDelete = ForeignKey.CASCADE)], indices = [Index("manager_id")] )
data class Farmer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val farmer_id: Int = 0,
    @ColumnInfo(name= "manager_id") val manager_id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "gender") var gender: Int,
    @ColumnInfo(name = "phone_number") var phone_number: String,
    @ColumnInfo(name = "birth_certificate") var birth_certificate: String,
    @ColumnInfo(name = "national_id") var national_id: String,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    @TypeConverters(Converters::class)
    var createdAt: Date?
    )
: Serializable