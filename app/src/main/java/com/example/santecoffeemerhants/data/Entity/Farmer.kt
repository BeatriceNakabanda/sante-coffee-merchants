package com.example.santecoffeemerhants.data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "farmer_table", foreignKeys = [ForeignKey(entity = RegionalManager::class,
    parentColumns = arrayOf("regional_manager_id"),
    childColumns = arrayOf("manager_id"),
    onDelete = ForeignKey.CASCADE)])
data class Farmer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val farmer_id: Long,
    @ColumnInfo(name= "manager_id") val manager_id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "phone_number") val phone_number: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val birth_certificate: ByteArray? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "modified_at") val modifiedAt: Long
): Serializable {
}