package ru.d3rvich.habittracker_compose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.d3rvich.api.db.HabitDatabase
import javax.inject.Singleton

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @[Singleton Provides]
    fun provideHabitDatabase(@ApplicationContext context: Context): HabitDatabase {
        return HabitDatabase(context)
    }
}