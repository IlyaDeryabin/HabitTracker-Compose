package ru.d3rvich.feature_habiteditor.data.repositories

import ru.d3rvich.api.db.HabitDatabase
import ru.d3rvich.feature_habiteditor.data.mappers.toHabitDto
import ru.d3rvich.feature_habiteditor.data.mappers.toHabitEntity
import ru.d3rvich.feature_habiteditor.domain.entities.HabitEntity
import ru.d3rvich.feature_habiteditor.domain.repositories.HabitEditorRepository

/**
 * Created by Ilya Deryabin at 29.06.2022
 */
internal class HabitEditorRepositoryImpl(habitDatabase: HabitDatabase) : HabitEditorRepository {
    private val habitDao = habitDatabase.habitDao()
    override suspend fun getHabitBy(id: String): HabitEntity =
        habitDao.getHabitBy(id = id).toHabitEntity()

    override suspend fun createHabit(habitEntity: HabitEntity) {
        habitDao.createHabits(habitEntity.toHabitDto())
    }

    override suspend fun updateHabit(habitEntity: HabitEntity) {
        habitDao.updateHabits(habitEntity.toHabitDto())
    }
}