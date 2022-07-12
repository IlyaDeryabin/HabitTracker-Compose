package ru.d3rvich.feature_habitlist.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import ru.d3rvich.core.ComposableFeatureEntry
import ru.d3rvich.core.di.FeatureEntryKey
import ru.d3rvich.feature_habitlist.HabitListFeatureEntryImpl
import ru.d3rvich.feature_habitlist_api.HabitListFeatureEntry
import javax.inject.Singleton

/**
 * Created by Ilya Deryabin at 12.07.2022
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface HabitListEntryModule {

    @Binds
    @IntoMap
    @Singleton
    @FeatureEntryKey(HabitListFeatureEntry::class)
    fun habitListEntry(entry: HabitListFeatureEntryImpl): ComposableFeatureEntry
}