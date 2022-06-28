package ru.d3rvich.habittracker_compose.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.d3rvich.feature_habitlist.deps.HabitListDeps
import javax.inject.Singleton

/**
 * Created by Ilya Deryabin at 28.06.2022
 */
@[Singleton Component(modules = [AppModule::class])]
interface AppComponent : HabitListDeps {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(application: Context): Builder

        fun build(): AppComponent
    }
}

@Module(includes = [DataModule::class])
object AppModule