package ru.d3rvich.habittracker_compose

import android.app.Application
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import ru.d3rvich.feature_habiteditor.deps.HabitEditorDeps
import ru.d3rvich.feature_habiteditor.deps.HabitEditorDepsStore
import ru.d3rvich.feature_habitlist.deps.HabitListDeps
import ru.d3rvich.feature_habitlist.deps.HabitListDepsStore

@HiltAndroidApp
class HabitTrackerApplication: Application() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Deps : HabitListDeps, HabitEditorDeps

    override fun onCreate() {
        super.onCreate()
        val point = EntryPointAccessors.fromApplication<Deps>(this)
        HabitListDepsStore.deps = point
        HabitEditorDepsStore.deps = point
    }
}