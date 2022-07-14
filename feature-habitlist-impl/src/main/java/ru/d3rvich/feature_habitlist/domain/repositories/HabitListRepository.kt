package ru.d3rvich.feature_habitlist.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.d3rvich.feature_habitlist.domain.entities.HabitEntity

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
internal interface HabitListRepository {
    fun getHabits(): Flow<List<HabitEntity>>

    suspend fun deleteHabit(habitEntity: HabitEntity)
}