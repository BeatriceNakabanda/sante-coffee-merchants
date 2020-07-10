package com.example.santecoffeemerhants.data.Entity

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "farmer_table", foreignKeys = [ForeignKey(entity = RegionalManager::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("manager_id"),
    onDelete = ForeignKey.CASCADE)], indices = [Index("manager_id")] )
data class Farmer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val farmer_id: Long,
    @ColumnInfo(name= "manager_id") val manager_id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "phone_number") val phone_number: String,
    @ColumnInfo(name = "birth_certificate") val birth_certificate: String,
//    @ColumnInfo(name = "national_id") val national_id: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "modified_at") val modifiedAt: Long
): Serializable {
}