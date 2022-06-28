package ru.d3rvich.feature_habitlist.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.d3rvich.api.db.HabitDatabase
import ru.d3rvich.feature_habitlist.data.mappers.toHabitDto
import ru.d3rvich.feature_habitlist.data.mappers.toHabitEntity
import ru.d3rvich.feature_habitlist.domain.entities.HabitEntity
import ru.d3rvich.feature_habitlist.domain.repositories.HabitListRepository

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
internal class HabitListRepositoryImpl(habitDatabase: HabitDatabase) : HabitListRepository {
    private val habitDao = habitDatabase.habitDao()
    override fun getHabits(): Flow<List<HabitEntity>> {
        return habitDao.getHabits().map { list -> list.map { habit -> habit.toHabitEntity() } }
    }

    override suspend fun deleteHabit(habitEntity: HabitEntity) {
        habitDao.deleteHabits(habitEntity.toHabitDto())
    }
}