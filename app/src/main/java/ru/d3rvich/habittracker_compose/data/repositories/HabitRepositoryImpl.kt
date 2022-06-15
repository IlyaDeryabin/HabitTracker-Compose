package ru.d3rvich.habittracker_compose.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.d3rvich.habittracker_compose.data.local.HabitDatabase
import ru.d3rvich.habittracker_compose.data.mappers.toHabitDto
import ru.d3rvich.habittracker_compose.data.mappers.toHabitEntity
import ru.d3rvich.habittracker_compose.entity.HabitEntity

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
class HabitRepositoryImpl(habitDatabase: HabitDatabase) : HabitRepository {

    private val habitDao = habitDatabase.habitDao()

    override fun getHabits(): Flow<List<HabitEntity>> =
        habitDao.getHabits().map { list -> list.map { it.toHabitEntity() } }

    override suspend fun createHabit(habitEntity: HabitEntity) =
        habitDao.createHabits(habitEntity.toHabitDto())

    override suspend fun getHabitBy(id: String): HabitEntity =
        habitDao.getHabitBy(id).toHabitEntity()

    override suspend fun updateHabit(habitEntity: HabitEntity) =
        habitDao.updateHabits(habitEntity.toHabitDto())

    override suspend fun deleteHabit(habitEntity: HabitEntity) =
        habitDao.deleteHabits(habitEntity.toHabitDto())
}