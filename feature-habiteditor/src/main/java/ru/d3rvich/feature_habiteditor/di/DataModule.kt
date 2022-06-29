package ru.d3rvich.feature_habiteditor.di

import dagger.Module
import dagger.Provides
import ru.d3rvich.api.db.HabitDatabase
import ru.d3rvich.feature_habiteditor.data.repositories.HabitEditorRepositoryImpl
import ru.d3rvich.feature_habiteditor.domain.repositories.HabitEditorRepository

/**
 * Created by Ilya Deryabin at 29.06.2022
 */
@Module
internal object DataModule {

    @Provides
    fun provideHabitEditorRepository(habitDatabase: HabitDatabase): HabitEditorRepository {
        return HabitEditorRepositoryImpl(habitDatabase = habitDatabase)
    }
}