package ru.d3rvich.habittracker_compose

import android.app.Application
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import ru.d3rvich.feature_habiteditor.deps.HabitEditorDeps
import ru.d3rvich.feature_habiteditor.deps.HabitEditorDepsStore

@HiltAndroidApp
class HabitTrackerApplication: Application() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Deps : HabitEditorDeps

    override fun onCreate() {
        super.onCreate()
        val point = EntryPointAccessors.fromApplication<Deps>(this)
        HabitEditorDepsStore.deps = point
    }
}