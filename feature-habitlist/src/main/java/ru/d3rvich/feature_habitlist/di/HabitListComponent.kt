package ru.d3rvich.feature_habitlist.di

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Component
import ru.d3rvich.api.db.HabitDatabase
import ru.d3rvich.core.Feature
import ru.d3rvich.feature_habitlist.presentation.HabitListFragment
import kotlin.properties.Delegates.notNull

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
@[Feature Component(dependencies = [HabitListDeps::class], modules = [DataModule::class])]
internal interface HabitListComponent {

    fun inject(fragment: HabitListFragment)

    @Component.Builder
    interface Builder {
        fun deps(habitListDeps: HabitListDeps): Builder

        fun build(): HabitListComponent
    }
}

interface HabitListDeps {
    val context: Context

    val habitDatabase: HabitDatabase
}

interface HabitListDepsProvider {
    val deps: HabitListDeps

    companion object : HabitListDepsProvider by HabitListDepsStore
}

object HabitListDepsStore : HabitListDepsProvider {
    override var deps: HabitListDeps by notNull()
}

internal class HabitListComponentViewModel : ViewModel() {
    val habitListComponent =
        DaggerHabitListComponent.builder().deps(HabitListDepsStore.deps).build()
}