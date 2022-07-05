package ru.d3rvich.habittracker_compose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.d3rvich.feature_habiteditor.HabitEditorFeatureImpl
import ru.d3rvich.feature_habiteditor_api.HabitEditorFeatureApi

/**
 * Created by Ilya Deryabin at 05.07.2022
 */
@[Module InstallIn(SingletonComponent::class)]
object FeatureModule {

    @Provides
    fun provideHabitEditorFeatureApi(): HabitEditorFeatureApi = HabitEditorFeatureImpl()
}