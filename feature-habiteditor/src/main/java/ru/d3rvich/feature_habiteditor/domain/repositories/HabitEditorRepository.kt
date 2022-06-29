package ru.d3rvich.feature_habiteditor.domain.repositories

import ru.d3rvich.feature_habiteditor.domain.entities.HabitEntity

/**
 * Created by Ilya Deryabin at 28.06.2022
 */
internal interface HabitEditorRepository {
    suspend fun getHabitBy(id: String): HabitEntity

    suspend fun createHabit(habitEntity: HabitEntity)

    suspend fun updateHabit(habitEntity: HabitEntity)
}