package ru.d3rvich.habittracker_compose.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
@Entity(tableName = "habit")
@TypeConverters(HabitConverters::class)
data class HabitDto(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val type: Int,
    val count: Int,
    val frequency: Int,
    val priority: Int,
    val color: Int,
    val date: Long,
    val doneDates: List<Long>,
)

class HabitConverters {
    private val separator = ","

    @TypeConverter
    fun fromDoneDates(doneDates: List<Long>): String {
        return doneDates.joinToString(separator)
    }

    @TypeConverter
    fun toDoneDates(data: String): List<Long> {
        if (data.isEmpty()) {
            return emptyList()
        }
        return data.split(separator).map { it.toLong() }
    }
}