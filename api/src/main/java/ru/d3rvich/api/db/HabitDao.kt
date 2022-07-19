package ru.d3rvich.api.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.d3rvich.api.dto.HabitDto

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
@Dao
interface HabitDao {

    @Query("SELECT * FROM habit")
    fun getHabits(): Flow<List<HabitDto>>

    @Query("SELECT * FROM habit WHERE id = :id")
    suspend fun getHabitBy(id: String): HabitDto

    @Insert
    suspend fun createHabits(vararg habitDto: HabitDto)

    @Update
    suspend fun updateHabits(vararg habitDto: HabitDto)

    @Delete
    suspend fun deleteHabits(vararg habitDto: HabitDto)
}