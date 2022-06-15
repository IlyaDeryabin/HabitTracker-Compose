package ru.d3rvich.habittracker_compose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.d3rvich.habittracker_compose.data.local.HabitDatabase
import ru.d3rvich.habittracker_compose.data.repositories.HabitRepository
import ru.d3rvich.habittracker_compose.data.repositories.HabitRepositoryImpl

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideHabitRepository(habitDatabase: HabitDatabase): HabitRepository {
        return HabitRepositoryImpl(habitDatabase = habitDatabase)
    }
}