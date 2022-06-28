package ru.d3rvich.habittracker_compose

import android.app.Application
import ru.d3rvich.feature_habitlist.di.HabitListDepsStore
import ru.d3rvich.habittracker_compose.di.AppComponent
import ru.d3rvich.habittracker_compose.di.DaggerAppComponent

class HabitTrackerApplication : Application() {
    internal val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().applicationContext(this).build()
    }

    init {
        HabitListDepsStore.deps = appComponent
    }
}