package ru.d3rvich.feature_habiteditor.domain.usecases

import ru.d3rvich.feature_habiteditor.domain.entities.HabitEntity
import ru.d3rvich.feature_habiteditor.domain.repositories.HabitEditorRepository
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 29.06.2022
 *
 * Use case создания привычки.
 */
internal class CreateHabitUseCase @Inject constructor(private val habitEditorRepository: HabitEditorRepository) {
    suspend operator fun invoke(habit: HabitEntity) {
        habitEditorRepository.createHabit(habitEntity = habit)
    }
}