package ru.d3rvich.feature_habitlist.domain.usecases

import ru.d3rvich.feature_habitlist.domain.entities.HabitEntity
import ru.d3rvich.feature_habitlist.domain.repositories.HabitListRepository
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 28.06.2022
 *
 * Use case удаления привычки
 */
internal class DeleteHabitUseCase @Inject constructor(private val habitListRepository: HabitListRepository) {
    suspend operator fun invoke(habitToRemove: HabitEntity) {
        habitListRepository.deleteHabit(habitEntity = habitToRemove)
    }
}