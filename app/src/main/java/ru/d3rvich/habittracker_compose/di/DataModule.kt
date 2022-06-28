package ru.d3rvich.habittracker_compose.di

import dagger.Module
import dagger.Provides
import ru.d3rvich.habittracker_compose.data.local.HabitDatabase
import ru.d3rvich.habittracker_compose.data.repositories.HabitRepository
import ru.d3rvich.habittracker_compose.data.repositories.HabitRepositoryImpl
import javax.inject.Singleton

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
@Module(includes = [DatabaseModule::class])
object DataModule {

    @Provides
    @Singleton
    fun provideHabitRepository(habitDatabase: HabitDatabase): HabitRepository {
        return HabitRepositoryImpl(habitDatabase = habitDatabase)
    }
}