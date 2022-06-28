package ru.d3rvich.habittracker_compose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.d3rvich.api.db.HabitDatabase
import javax.inject.Singleton

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
@Module
object DatabaseModule {

    @[Singleton Provides]
    fun provideHabitDatabase(context: Context): HabitDatabase {
        return HabitDatabase(context)
    }
}