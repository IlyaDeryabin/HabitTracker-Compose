package ru.d3rvich.feature_habiteditor.domain.usecases

import ru.d3rvich.feature_habiteditor.domain.entities.HabitEntity
import ru.d3rvich.feature_habiteditor.domain.repositories.HabitEditorRepository
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 29.06.2022
 *
 * Use case получения привычки по его Id.
 */
internal class GetHabitUseCase @Inject constructor(private val habitEditorRepository: HabitEditorRepository) {
    suspend operator fun invoke(habitId: String): HabitEntity =
        habitEditorRepository.getHabitBy(id = habitId)
}