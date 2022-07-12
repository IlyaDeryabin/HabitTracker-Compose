package ru.d3rvich.core.di

import dagger.MapKey
import ru.d3rvich.core.ComposableFeatureEntry
import kotlin.reflect.KClass

/**
 * Created by Ilya Deryabin at 12.07.2022
 */
@MapKey
annotation class FeatureEntryKey(val value: KClass<out ComposableFeatureEntry>)