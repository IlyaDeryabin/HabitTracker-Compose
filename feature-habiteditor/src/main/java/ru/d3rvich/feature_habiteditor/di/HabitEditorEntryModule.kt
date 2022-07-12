package ru.d3rvich.feature_habiteditor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import ru.d3rvich.core.ComposableFeatureEntry
import ru.d3rvich.core.di.FeatureEntryKey
import ru.d3rvich.feature_habiteditor.HabitEditorFeatureEntryImpl
import ru.d3rvich.feature_habiteditor_api.HabitEditorFeatureEntry
import javax.inject.Singleton

/**
 * Created by Ilya Deryabin at 12.07.2022
 *
 * В чём моё предназначеие?
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface HabitEditorEntryModule {

    @Singleton
    @Binds
    @IntoMap
    @FeatureEntryKey(HabitEditorFeatureEntry::class)
    fun habitEditorEntry(entry: HabitEditorFeatureEntryImpl): ComposableFeatureEntry
}