package ru.d3rvich.feature_habiteditor.deps

import androidx.lifecycle.ViewModel
import ru.d3rvich.api.db.HabitDatabase
import ru.d3rvich.feature_habiteditor.di.DaggerHabitEditorComponent
import ru.d3rvich.feature_habiteditor.di.HabitEditorComponent
import kotlin.properties.Delegates.notNull

/**
 * Created by Ilya Deryabin at 28.06.2022
 */
interface HabitEditorDeps {
    val habitDatabase: HabitDatabase
}

interface HabitEditorNavRouter {
    fun popBack()
}

interface HabitEditorDepsProvider {
    val deps: HabitEditorDeps

    val navRouter: HabitEditorNavRouter

    companion object : HabitEditorDepsProvider by HabitEditorDepsStore
}

object HabitEditorDepsStore : HabitEditorDepsProvider {
    override var deps: HabitEditorDeps by notNull()
    override var navRouter: HabitEditorNavRouter by notNull()
}

internal class HabitEditorComponentViewModel : ViewModel() {
    internal val habitEditorComponent: HabitEditorComponent =
        DaggerHabitEditorComponent.builder().deps(HabitEditorDepsProvider.deps).build()
}