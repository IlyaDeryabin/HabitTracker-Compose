package ru.d3rvich.habittracker_compose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow
import ru.d3rvich.habittracker_compose.data.dto.HabitDto

/**
 * Created by Ilya Deryabin at 15.06.2022
 */
@Dao
interface HabitDao {

    @Query("SELECT * FROM habit")
    fun getHabits(): Flow<List<HabitDto>>

    @Query("SELECT * FROM habit")
    suspend fun getHabitsSnapshot(): List<HabitDto>

    @Query("SELECT * FROM habit WHERE id = :id")
    suspend fun getHabitBy(id: String): HabitDto

    @Insert
    suspend fun createHabits(vararg habitDto: HabitDto)

    @Update
    suspend fun updateHabits(vararg habitDto: HabitDto)

    @Delete
    suspend fun deleteHabits(vararg habitDto: HabitDto)
}