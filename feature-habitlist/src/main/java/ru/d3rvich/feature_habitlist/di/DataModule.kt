package ru.d3rvich.feature_habitlist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.d3rvich.api.db.HabitDatabase
import ru.d3rvich.feature_habitlist.data.repositories.HabitListRepositoryImpl
import ru.d3rvich.feature_habitlist.domain.repositories.HabitListRepository

/**
 * Created by Ilya Deryabin at 27.06.2022
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    fun provideHabitListRepository(habitDatabase: HabitDatabase): HabitListRepository {
        return HabitListRepositoryImpl(habitDatabase = habitDatabase)
    }
}