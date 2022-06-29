package ru.d3rvich.feature_habitlist.deps

import androidx.lifecycle.ViewModel
import ru.d3rvich.api.db.HabitDatabase
import ru.d3rvich.feature_habitlist.di.DaggerHabitListComponent
import kotlin.properties.Delegates.notNull

/**
 * Created by Ilya Deryabin at 28.06.2022
 */
interface HabitListDeps {
    val habitDatabase: HabitDatabase
}

interface HabitListNavRouter {
    fun navigateToHabitCreator()

    fun navigateToHabitEditorBy(id: String)
}

interface HabitListDepsProvider {
    val deps: HabitListDeps

    val navRouter: HabitListNavRouter

    companion object : HabitListDepsProvider by HabitListDepsStore
}

object HabitListDepsStore : HabitListDepsProvider {
    override var deps: HabitListDeps by notNull()
    override var navRouter: HabitListNavRouter by notNull()
}

internal class HabitListComponentViewModel : ViewModel() {
    val habitListComponent =
        DaggerHabitListComponent.builder().deps(HabitListDepsStore.deps).build()
}