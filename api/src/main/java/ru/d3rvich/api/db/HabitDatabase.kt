package ru.d3rvich.api.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.d3rvich.api.dto.HabitDto

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
@Database(version = 1, exportSchema = false, entities = [HabitDto::class])
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}

fun HabitDatabase(context: Context): HabitDatabase =
    Room.databaseBuilder(context, HabitDatabase::class.java, DATABASE_NAME).build()

private const val DATABASE_NAME = "habit-database"