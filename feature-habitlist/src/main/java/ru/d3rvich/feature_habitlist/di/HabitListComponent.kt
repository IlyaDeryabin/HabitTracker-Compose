package ru.d3rvich.feature_habitlist.di

import dagger.Component
import ru.d3rvich.core.Feature
import ru.d3rvich.feature_habitlist.deps.HabitListDeps
import ru.d3rvich.feature_habitlist.presentation.HabitListViewModel

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
@[Feature Component(dependencies = [HabitListDeps::class], modules = [DataModule::class])]
internal interface HabitListComponent {

    val habitListViewModelFactory: HabitListViewModel.Factory

    @Component.Builder
    interface Builder {
        fun deps(habitListDeps: HabitListDeps): Builder

        fun build(): HabitListComponent
    }
}