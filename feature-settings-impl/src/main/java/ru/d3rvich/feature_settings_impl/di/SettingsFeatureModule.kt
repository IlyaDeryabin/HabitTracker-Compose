package ru.d3rvich.feature_settings_impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import ru.d3rvich.core.di.FeatureEntryKey
import ru.d3rvich.core.feature.FeatureEntry
import ru.d3rvich.feature_settings_api.SettingsFeatureEntry
import ru.d3rvich.feature_settings_impl.SettingsFeatureEntryImpl
import javax.inject.Singleton

/**
 * Created by Ilya Deryabin at 14.07.2022
 *
 * DI модуль для добавления entry фичи в мапу
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface SettingsFeatureModule {

    @Singleton
    @Binds
    @IntoMap
    @FeatureEntryKey(SettingsFeatureEntry::class)
    fun settingsEntry(entry: SettingsFeatureEntryImpl): FeatureEntry
}