package ru.d3rvich.habittracker_compose.di

import dagger.Module

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
@Module(includes = [DatabaseModule::class])
object DataModule