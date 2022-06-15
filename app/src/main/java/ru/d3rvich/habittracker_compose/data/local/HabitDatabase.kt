package ru.d3rvich.habittracker_compose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.d3rvich.habittracker_compose.data.dto.HabitDto

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
@Database(version = 1, exportSchema = false, entities = [HabitDto::class])
abstract class HabitDatabase: RoomDatabase() {
    abstract fun habitDao(): HabitDao
}