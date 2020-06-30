package com.example.santecoffeemerhants.data.converter

import androidx.room.TypeConverter
import java.util.*
/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {

        return when (value) {
            null -> null
            else -> Date(value)
        }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {

        return when (date) {
            null -> null
            else -> date.time
        }
    }
}