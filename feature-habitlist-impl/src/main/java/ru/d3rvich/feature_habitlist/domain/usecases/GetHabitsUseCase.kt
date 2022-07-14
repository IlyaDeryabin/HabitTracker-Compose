package ru.d3rvich.feature_habitlist.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.d3rvich.feature_habitlist.domain.entities.HabitEntity
import ru.d3rvich.feature_habitlist.domain.repositories.HabitListRepository
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 28.06.2022
 *
 * Use case получения Flow списка привычек
 */
internal class GetHabitsUseCase @Inject constructor(private val habitListRepository: HabitListRepository) {
    operator fun invoke(): Flow<List<HabitEntity>> {
        return habitListRepository.getHabits()
    }
}