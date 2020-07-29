package com.example.santecoffeemerhants.data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.santecoffeemerhants.data.converter.Converters
import java.io.Serializable
import java.util.*

@Entity(tableName = "regional_manager")
data class RegionalManager(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var regional_manager_id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "gender") var gender: Int,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "contact") var contact: String,
    @ColumnInfo(name = "region") var region: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    @TypeConverters(Converters::class)
    var createdAt: Date?
) : Serializable