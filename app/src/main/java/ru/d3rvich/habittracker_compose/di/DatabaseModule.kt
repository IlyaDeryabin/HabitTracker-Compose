package ru.d3rvich.habittracker_compose.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.d3rvich.habittracker_compose.data.local.HabitDatabase
import javax.inject.Singleton

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHabitDatabase(@ApplicationContext context: Context): HabitDatabase {
        return Room.databaseBuilder(context, HabitDatabase::class.java, DATABASE_NAME).build()
    }
}

private const val DATABASE_NAME = "habit-database"