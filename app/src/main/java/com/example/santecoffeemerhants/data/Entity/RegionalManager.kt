package com.example.santecoffeemerhants.data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "regional_manager")
data class RegionalManager(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var regional_manager_id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "region") var region: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "created_at") var createdAt: Long

    )
    :Serializable {
}