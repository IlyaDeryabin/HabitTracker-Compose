package ru.d3rvich.feature_habiteditor.di

import dagger.Component
import ru.d3rvich.core.Feature
import ru.d3rvich.feature_habiteditor.deps.HabitEditorDeps
import ru.d3rvich.feature_habiteditor.presenter.HabitEditorFragment

/**
 * Created by Ilya Deryabin at 28.06.2022
 */
@[Feature Component(dependencies = [HabitEditorDeps::class], modules = [DataModule::class])]
internal interface HabitEditorComponent {

    fun inject(fragment: HabitEditorFragment)

    @Component.Builder
    interface Builder {
        fun deps(habitEditorDeps: HabitEditorDeps): Builder

        fun build(): HabitEditorComponent
    }
}