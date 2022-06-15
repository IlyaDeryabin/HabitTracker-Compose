package ru.d3rvich.habittracker_compose.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.d3rvich.habittracker_compose.entity.HabitEntity

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
interface HabitRepository {
    fun getHabits(): Flow<List<HabitEntity>>

    suspend fun getHabitBy(id: String): HabitEntity

    suspend fun createHabit(habitEntity: HabitEntity)

    suspend fun updateHabit(habitEntity: HabitEntity)

    suspend fun deleteHabit(habitEntity: HabitEntity)
}